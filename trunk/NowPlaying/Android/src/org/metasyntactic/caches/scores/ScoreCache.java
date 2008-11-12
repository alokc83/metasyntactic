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

import org.metasyntactic.NowPlayingModel;
import org.metasyntactic.data.Movie;
import org.metasyntactic.data.Review;
import org.metasyntactic.data.Score;

import java.util.List;

public class ScoreCache {
  private final ScoreProvider rottenTomatoesScoreProvider = new RottenTomatoesScoreProvider(this);
  private final ScoreProvider metacriticScoreProvider = new MetacriticScoreProvider(this);
  private final ScoreProvider googleScoreProvider = new GoogleScoreProvider(this);
  private final ScoreProvider noneScoreProvider = new NoneScoreProvider(this);

  private final NowPlayingModel model;

  public ScoreCache(NowPlayingModel model) {
    this.model = model;
  }

  public void createDirectories() {
    rottenTomatoesScoreProvider.createDirectory();
    metacriticScoreProvider.createDirectory();
    googleScoreProvider.createDirectory();
    noneScoreProvider.createDirectory();
  }

  private ScoreProvider getCurrentScoreProvider() {
    if (model.getScoreType() == ScoreType.Google) {
      return googleScoreProvider;
    } else if (model.getScoreType() == ScoreType.Metacritic) {
      return metacriticScoreProvider;
    } else if (model.getScoreType() == ScoreType.RottenTomatoes) {
      return rottenTomatoesScoreProvider;
    } else if (model.getScoreType() == ScoreType.None) {
      return noneScoreProvider;
    } else {
      throw new RuntimeException();
    }
  }

  public Score getScore(List<Movie> movies, Movie movie) {
    return getCurrentScoreProvider().getScore(movies, movie);
  }

  public void update() {
    getCurrentScoreProvider().update();
  }

  NowPlayingModel getModel() {
    return model;
  }

  public List<Review> getReviews(List<Movie> movies, Movie movie) {
    return getCurrentScoreProvider().getReviews(movies, movie);
  }

  public void prioritizeMovie(Movie movie) {
    getCurrentScoreProvider().prioritizeMovie(movie);
  }
}