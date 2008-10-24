//Copyright 2008 Cyrus Najmabadi
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package org.metasyntactic.caches.scores;

import org.metasyntactic.Application;
import org.metasyntactic.Constants;
import org.metasyntactic.NowPlayingModel;
import org.metasyntactic.data.Location;
import org.metasyntactic.data.Movie;
import org.metasyntactic.data.Review;
import org.metasyntactic.data.Score;
import org.metasyntactic.threading.ThreadingUtilities;
import org.metasyntactic.utilities.*;
import static org.metasyntactic.utilities.CollectionUtilities.size;
import static org.metasyntactic.utilities.XmlUtilities.children;
import org.metasyntactic.utilities.difference.EditDistance;
import org.w3c.dom.Element;

import java.io.File;
import java.util.*;

/** @author cyrusn@google.com (Cyrus Najmabadi) */
public abstract class AbstractScoreProvider implements ScoreProvider {
  private final ScoreCache parentCache;
  private final Object lock = new Object();

  private Map<String, Score> scores = Collections.emptyMap();
  private String hash = "";


  private final Object movieMapLock = new Object();
  private List<Movie> movies = Collections.emptyList();
  private Map<String, String> movieMap = Collections.emptyMap();

  private final File reviewsDirectory = new File(Application.reviewsDirectory, getProviderName());


  public AbstractScoreProvider(ScoreCache parentCache) {
    this.parentCache = parentCache;

    createDirectory();
  }


  public void createDirectory() {
    reviewsDirectory.mkdirs();
  }


  protected abstract String getProviderName();


  protected NowPlayingModel getModel() {
    return parentCache.getModel();
  }


  private File ratingsFile() {
    return new File(Application.scoresDirectory, getProviderName());
  }


  private File hashFile() {
    return new File(Application.scoresDirectory, getProviderName() + "-Hash");
  }


  private Map<String, Score> loadScores() {
    Map<String, Score> result =
        FileUtilities.readStringToPersistableMap(Score.reader, ratingsFile());
    if (result == null) {
      result = Collections.emptyMap();
    }
    return result;
  }


  private String loadHash() {
    String string = FileUtilities.readString(hashFile());
    if (string == null) {
      return "";
    }
    return string;
  }


  public Map<String, Score> getScores() {
    if (scores == null) {
      scores = loadScores();
    }
    return scores;
  }


  private String getHash() {
    if (hash == null) {
      hash = loadHash();
    }
    return hash;
  }


  public void update() {
    updateScores();
    updateReviews();
  }


  private void updateScores() {
    Runnable runnable = new Runnable() {
      public void run() {
        updateScoresBackgroundEntryPoint();
      }
    };
    ThreadingUtilities.performOnBackgroundThread("Update Scores", runnable, lock, true/*visible*/,
        Thread.MIN_PRIORITY + 1);
  }


  private void updateReviews() {
    final Map<String, Score> scores = getScores();

    Runnable runnable = new Runnable() {
      public void run() {
        updateReviewsBackgroundEntryPoint(scores);
      }
    };
    ThreadingUtilities.performOnBackgroundThread("Update Reviews", runnable, lock, false/*visible*/,
        Thread.MIN_PRIORITY + 1);
  }


  private void updateScoresBackgroundEntryPoint() {
    long start = System.currentTimeMillis();
    updateScoresBackgroundEntryPointWorker();
    LogUtilities.logTime(getClass(), "Update Scores", start);

    Application.refresh(true);
  }


  private void updateReviewsBackgroundEntryPoint(Map<String, Score> scores) {
    long start = System.currentTimeMillis();
    updateReviewsBackgroundEntryPointWorker(scores);
    LogUtilities.logTime(getClass(), "Update Reviews", start);
  }


  private void updateScoresBackgroundEntryPointWorker() {
    if (FileUtilities.tooSoon(hashFile())) {
      return;
    }

    String localHash = getHash();
    final String serverHash = lookupServerHash();

    if (StringUtilities.isNullOrEmpty(serverHash)) {
      return;
    }

    if ("0".equals(serverHash)) {
      return;
    }

    if (localHash.equals(serverHash)) {
      return;
    }

    final Map<String, Score> result = lookupServerRatings();

    if (CollectionUtilities.isEmpty(result)) {
      return;
    }

    reportResult(serverHash, result);
    saveResult(serverHash, result);
  }


  private void saveResult(String serverHash, Map<String, Score> result) {
    FileUtilities.writeStringToPersistableMap(result, ratingsFile());

    // write this file last, to indicate that we are done.
    FileUtilities.writeString(serverHash, hashFile());
  }


  private void reportResult(final String hash, final Map<String, Score> scores) {
    Runnable runnable = new Runnable() {
      public void run() {
        reportResultOnMainThread(hash, scores);
      }
    };
    ThreadingUtilities.performOnMainThread(runnable);
  }


  private void reportResultOnMainThread(String hash, Map<String, Score> scores) {
    this.hash = hash;
    this.scores = scores;
    movieMap = Collections.emptyMap();

    updateReviews();
  }


  public Score getScore(final List<Movie> movies, Movie movie) {
    if (movieMap.isEmpty() || movies != this.movies) {
      this.movies = movies;
      final Map<String, Score> scores = getScores();

      Runnable runnable = new Runnable() {
        public void run() {
          regenerateMovieMap(movies, scores);
        }
      };
      ThreadingUtilities.performOnBackgroundThread("Regenerate Movie Map", runnable, movieMapLock, false);
    }

    return scores.get(movieMap.get(movie.getCanonicalTitle()));
  }


  private void regenerateMovieMap(List<Movie> movies, Map<String, Score> scores) {
    final Map<String, String> result = new HashMap<String, String>();

    List<String> titles = new ArrayList<String>(scores.keySet());
    List<String> lowercaseTitles = new ArrayList<String>();
    for (String title : titles) {
      lowercaseTitles.add(title.toLowerCase());
    }

    for (Movie movie : movies) {
      String lowercaseTitle = movie.getCanonicalTitle().toLowerCase();
      int index = EditDistance.findClosestMatchIndex(lowercaseTitle, lowercaseTitles);

      if (index >= 0) {
        String title = titles.get(index);
        result.put(movie.getCanonicalTitle(), title);
      }
    }

    if (result.isEmpty()) {
      return;
    }

    //FileUtilities.writeObject(result, movieMapFile());

    Runnable runnable = new Runnable() {
      public void run() {
        reportMovieMap(result);
      }
    };

    ThreadingUtilities.performOnMainThread(runnable);
  }


  private void reportMovieMap(Map<String, String> result) {
    movieMap = result;
    Application.refresh();
  }


  protected abstract String lookupServerHash();


  protected abstract Map<String, Score> lookupServerRatings();


  private File reviewsFile(String title) {
    return new File(reviewsDirectory, FileUtilities.sanitizeFileName(title));
  }


  private File reviewsHashFile(String title) {
    return new File(reviewsDirectory, FileUtilities.sanitizeFileName(title) + "-Hash");
  }


  private void updateReviewsBackgroundEntryPointWorker(Map<String, Score> scores) {
    Map<String, Score> scoresWithoutReviews = new HashMap<String, Score>();
    Map<String, Score> scoresWithReviews = new HashMap<String, Score>();

    for (Map.Entry<String, Score> entry : scores.entrySet()) {
      File file = reviewsFile(entry.getKey());

      if (!file.exists()) {
        scoresWithoutReviews.put(entry.getKey(), entry.getValue());
      } else {
        if (Math.abs(new Date().getTime() - file.lastModified()) > (2 * Constants.ONE_DAY)) {
          scoresWithReviews.put(entry.getKey(), entry.getValue());
        }
      }
    }

    downloadReviews(scoresWithoutReviews);
    downloadReviews(scoresWithReviews);
  }


  private void downloadReviews(Map<String, Score> scores) {
    Location location = getModel().getUserLocationCache().downloadUserAddressLocationBackgroundEntryPoint(
        getModel().getUserLocation());

    if (location == null) {
      return;
    }

    for (Map.Entry<String, Score> entry : scores.entrySet()) {
      downloadReviews(entry.getKey(), entry.getValue(), location);
    }
  }


  private String serverReviewsAddress(Location location, Score score) {
    String country = Locale.getDefault().getCountry();
    if (!StringUtilities.isNullOrEmpty(location.getCountry())) {
      country = location.getCountry();
    }

    String address = "http://" + Application.host + ".appspot.com/LookupMovieReviews2?country=" + country +
        "&language=" + Locale.getDefault().getLanguage() +
        "&id=" + score.getIdentifier() + "" +
        "&provider=" + score.getProvider() +
        "&latitude=" + (int) (location.getLatitude() * 1000000) +
        "&longitude=" + (int) (location.getLongitude() * 1000000);

    return address;
  }


  private void downloadReviews(String title, Score score, Location location) {
    String address = serverReviewsAddress(location, score) + "&hash=true";
    String serverHash = NetworkUtilities.downloadString(address, false);

    if (serverHash == null) {
      serverHash = "0";
    }

    String localHash = FileUtilities.readString(reviewsHashFile(title));
    if (serverHash.equals(localHash)) {
      return;
    }

    List<Review> reviews = downloadReviewContents(location, score);
    if (reviews == null) {
      // didn't download.  just ignore it.
      return;
    }


    if (reviews.isEmpty()) {
      // we got no reviews.  only save that fact if we don't currently have
      // any reviews.  This way we don't end up checking every single time
      // for movies that don't have reviews yet
      List<Review> existingReviews = FileUtilities.readPersistableList(Review.reader, reviewsFile(title));
      if (size(existingReviews) > 0) {
        // we have reviews already.  don't wipe it out.
        return;
      }
    }

    save(title, reviews, serverHash);
    Application.refresh();
  }


  private List<Review> downloadReviewContents(Location location, Score score) {
    String address = serverReviewsAddress(location, score);
    Element element = NetworkUtilities.downloadXml(address, false);
    if (element == null) {
      return null;
    }

    return extractReviews(element);
  }


  private List<Review> extractReviews(Element element) {
    List<Review> result = new ArrayList<Review>();
    for (Element reviewElement : children(element)) {
      String text = reviewElement.getAttribute("text");
      String score = reviewElement.getAttribute("score");
      String link = reviewElement.getAttribute("link");
      String author = reviewElement.getAttribute("author");
      String source = reviewElement.getAttribute("source");

      if (author.contains("HREF")) {
        continue;
      }

      int scoreValue = -1;
      try {
        scoreValue = Integer.parseInt(score);
      } catch (NumberFormatException e) {
      }

      result.add(new Review(text, scoreValue, link, author, source));
    }

    return result;
  }


  private void save(String title, List<Review> reviews, String serverHash) {
    FileUtilities.writePersistableCollection(reviews, reviewsFile(title));

    // do this last.  it marks us being complete.
    FileUtilities.writeString(serverHash, reviewsHashFile(title));
  }
}
