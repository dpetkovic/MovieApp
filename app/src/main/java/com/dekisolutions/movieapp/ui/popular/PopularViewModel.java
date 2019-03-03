package com.dekisolutions.movieapp.ui.popular;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.dekisolutions.movieapp.data.MovieRepository;
import com.dekisolutions.movieapp.data.model.Movie;
import com.dekisolutions.movieapp.ui.common.BaseViewModel;
import java.util.ArrayList;
import java.util.List;

public class PopularViewModel extends BaseViewModel {
	
	private final MovieRepository moviesRepository;
	
	private MutableLiveData<List<Movie>> movieLiveData = new MutableLiveData<>();
	
	public PopularViewModel(MovieRepository movieRepository) {
		this.moviesRepository = movieRepository;
	}
	
	public void init() {
		moviesRepository.fetchMovies();
		addDisposable(moviesRepository.getMoviesByPopularity().map(movies -> {
			for (Movie movie : movies) {
				ArrayList<String> genreNames = new ArrayList<>();
				if (movie.getGenreIds() != null) {
					for (Integer id : movie.getGenreIds()) {
						genreNames.add(moviesRepository.getGenreName(id));
					}
				}
				movie.setGenreNames(genreNames);
			}
			return movies;
		}).subscribe(movies -> movieLiveData.postValue(movies)));
	}
	
	public LiveData<List<Movie>> getPopularMovies() {
		init();
		return movieLiveData;
	}
	
	@Override
	protected void onCleared() {
		super.onCleared();
		moviesRepository.clearAll();
	}
}
