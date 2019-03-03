package com.dekisolutions.movieapp.ui.favorite;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.dekisolutions.movieapp.data.MovieRepository;
import com.dekisolutions.movieapp.data.model.Movie;
import com.dekisolutions.movieapp.ui.common.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends BaseViewModel {
	
	private MovieRepository movieRepository;
	private MutableLiveData<List<Movie>> movieMutableLiveData =  new MutableLiveData<>();
	
	public FavoriteViewModel(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
		init();
	}
	
	private void init() {
		addDisposable(movieRepository.getMoviesByFavorite().map(movies -> {
				for (Movie movie : movies) {
					ArrayList<String> genreNames = new ArrayList<>();
					if (movie.getGenreIds() != null) {
						for (Integer id : movie.getGenreIds()) {
							genreNames.add(movieRepository.getGenreName(id));
						}
					}
					movie.setGenreNames(genreNames);
				}
				return movies;
			}).subscribeOn(AndroidSchedulers.mainThread()).subscribe(movies -> {
				movieMutableLiveData.postValue(movies);
			}));
	}
	
	public LiveData<List<Movie>> getMoviesByFavorite() {
		return movieMutableLiveData;
	}
	
	
	public void removeFavorite(int movieId) {
		movieRepository.removeFavorite(movieId);
	}
	
	@Override
	protected void onCleared() {
		super.onCleared();
		movieRepository.clearAll();
	}
}
