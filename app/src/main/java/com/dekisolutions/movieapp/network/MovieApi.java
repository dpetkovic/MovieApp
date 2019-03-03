package com.dekisolutions.movieapp.network;

import com.dekisolutions.movieapp.network.model.CreditsResponse;
import com.dekisolutions.movieapp.network.model.GenreResponse;
import com.dekisolutions.movieapp.network.model.MoviesResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
	
	@GET("3/movie/top_rated")
	Single<MoviesResponse> getMovies(@Query("api_key") String apiKey);
	
	@GET("3/genre/movie/list")
	Single<GenreResponse> getGenres(@Query("api_key") String apiKey);
	
	@GET("3/movie/{movie_id}/credits")
	Single<CreditsResponse> getCredits(@Path(value = "movie_id") int movieId, @Query("api_key") String apiKey);
}
