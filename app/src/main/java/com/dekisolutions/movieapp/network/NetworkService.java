package com.dekisolutions.movieapp.network;

import com.dekisolutions.movieapp.network.model.CreditsResponse;
import com.dekisolutions.movieapp.network.model.GenreResponse;
import com.dekisolutions.movieapp.network.model.MoviesResponse;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
	
	private static final String BASE_URL = "https://api.themoviedb.org/";
	private static final String API_KEY = "c22d755514350d9836b3f9b173b3d763";
	
	private final Retrofit retrofit;
	private MovieApi movieApi;
	
	public NetworkService() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
		
		retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
			.client(client)
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.build();
		
		movieApi = retrofit.create(MovieApi.class);
	}
	
	public Single<MoviesResponse> getMovies() {
		return movieApi.getMovies(API_KEY);
	}
	
	public Single<GenreResponse> getGenre() {
		return movieApi.getGenres(API_KEY);
	}
	
	public Single<CreditsResponse> getCredits(int movieId) {
		return movieApi.getCredits(movieId, API_KEY);
	}
}
