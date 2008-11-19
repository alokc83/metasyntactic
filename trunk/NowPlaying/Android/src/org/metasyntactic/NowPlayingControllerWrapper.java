// Copyright 2008 Cyrus Najmabadi
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.metasyntactic;

import android.app.Activity;
import android.content.Context;
import org.metasyntactic.caches.scores.ScoreType;
import org.metasyntactic.data.*;
import org.metasyntactic.threading.ThreadingUtilities;
import org.metasyntactic.ui.GlobalActivityIndicator;
import org.metasyntactic.utilities.SetUtilities;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** @author cyrusn@google.com (Cyrus Najmabadi) */
public class NowPlayingControllerWrapper {

  private static final Set<Activity> activities = new LinkedHashSet<Activity>();
  private static NowPlayingController instance;

  static {
    Application.initialize();
  }

  private NowPlayingControllerWrapper() {
  }

  public static void addActivity(final Activity activity) {
    checkThread();
    activities.add(activity);
    GlobalActivityIndicator.addActivity(activity);

    if (activities.size() == 1) {
      instance = new NowPlayingController(activity.getApplicationContext());
      instance.startup();
    }
  }

  public static void removeActivity(final Activity activity) {
   checkThread();
    GlobalActivityIndicator.removeActivity(activity);
    activities.remove(activity);

    if (activities.size() == 0) {
      instance.shutdown();
      instance = null;
    }
  }

  private static void checkThread() {
    if (!ThreadingUtilities.isMainThread()) {
      throw new RuntimeException("Trying to call into the controller on a background thread");
    }
  }

  private static void checkInstance() {
   checkThread();
    if (instance == null) {
      throw new RuntimeException("Trying to call into the controller when it does not exist");
    }
  }

  public static Context getApplicationContext() {
    checkInstance();
    return SetUtilities.any(activities).getApplicationContext();
  }

  public static String getUserLocation() {
    checkInstance();
    return instance.getUserLocation();
  }

  public static void setUserLocation(final String userLocation) {
    checkInstance();
    instance.setUserLocation(userLocation);
  }

  public static int getSearchDistance() {
    checkInstance();
    return instance.getSearchDistance();
  }

  public static void setSearchDistance(final int searchDistance) {
    checkInstance();
    instance.setSearchDistance(searchDistance);
  }

  public static int getSelectedTabIndex() {
    checkInstance();
    return instance.getSelectedTabIndex();
  }

  public static void setSelectedTabIndex(final int index) {
    checkInstance();
    instance.setSelectedTabIndex(index);
  }

  public static int getAllMoviesSelectedSortIndex() {
    checkInstance();
    return instance.getAllMoviesSelectedSortIndex();
  }

  public static void setAllMoviesSelectedSortIndex(final int index) {
    checkInstance();
    instance.setAllMoviesSelectedSortIndex(index);
  }

  public static int getAllTheatersSelectedSortIndex() {
    checkInstance();
    return instance.getAllTheatersSelectedSortIndex();
  }

  public static void setAllTheatersSelectedSortIndex(final int index) {
    checkInstance();
    instance.setAllTheatersSelectedSortIndex(index);
  }

  public static int getUpcomingMoviesSelectedSortIndex() {
    checkInstance();
    return instance.getUpcomingMoviesSelectedSortIndex();
  }

  public static void setUpcomingMoviesSelectedSortIndex(final int index) {
    checkInstance();
    instance.setUpcomingMoviesSelectedSortIndex(index);
  }

  public static List<Movie> getMovies() {
    checkInstance();
    return instance.getMovies();
  }

  public static List<Theater> getTheaters() {
    checkInstance();
    return instance.getTheaters();
  }

  public static List<String> getTrailers(final Movie movie) {
    checkInstance();
    return instance.getTrailers(movie);
  }

  public static List<Review> getReviews(final Movie movie) {
    checkInstance();
    return instance.getReviews(movie);
  }

  public static String getImdbAddress(final Movie movie) {
    checkInstance();
    return instance.getImdbAddress(movie);
  }

  public static List<Theater> getTheatersShowingMovie(final Movie movie) {
    checkInstance();
    return instance.getTheatersShowingMovie(movie);
  }

  public static List<Movie> getMoviesAtTheater(final Theater theater) {
    checkInstance();
    return instance.getMoviesAtTheater(theater);
  }

  public static List<Performance> getPerformancesForMovieAtTheater(final Movie movie, final Theater theater) {
    checkInstance();
    return instance.getPerformancesForMovieAtTheater(movie, theater);
  }

  public static ScoreType getScoreType() {
    checkInstance();
    return instance.getScoreType();
  }

  public static void setScoreType(final ScoreType scoreType) {
    checkInstance();
    instance.setScoreType(scoreType);
  }

  public static Score getScore(final Movie movie) {
    checkInstance();
    return instance.getScore(movie);
  }

  public static byte[] getPoster(final Movie movie) {
    checkInstance();
    return instance.getPoster(movie);
  }

  public static String getSynopsis(final Movie movie) {
    checkInstance();
    return instance.getSynopsis(movie);
  }

  public static void prioritizeMovie(final Movie movie) {
    checkInstance();
    instance.prioritizeMovie(movie);
  }

  public static boolean isAutoUpdateEnabled() {
    checkInstance();
    return instance.isAutoUpdateEnabled();
  }

  public static void setAutoUpdateEnabled(boolean enabled) {
    checkInstance();
    instance.setAutoUpdateEnabled(enabled);
  }
}
