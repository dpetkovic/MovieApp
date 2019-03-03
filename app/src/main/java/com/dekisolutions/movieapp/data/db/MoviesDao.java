package com.dekisolutions.movieapp.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.dekisolutions.movieapp.data.model.Movie;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;

@Dao
public interface MoviesDao {
	
	@Query("SELECT * FROM Movie ORDER BY voteAverage DESC")
	Flowable<List<Movie>> getMoviesByRating();
	
	@Query("SELECT * FROM Movie ORDER BY popularity DESC")
	Flowable<List<Movie>> getMoviesByPopularity();
	
	@Query("SELECT * FROM Movie WHERE id IN (:ids)")
	Flowable<List<Movie>> getMoviesByFavorite(List<Integer> ids);
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(List<Movie> movieList);
	
	@Query("SELECT * FROM Movie WHERE id = :movieId")
	Flowable<Movie> getMovie(int movieId);
	
	@Query("SELECT * FROM Movie WHERE title LIKE :query")
	Single<List<Movie>> getMovies(String query);
}
