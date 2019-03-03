package com.dekisolutions.movieapp.ui.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.dekisolutions.movieapp.data.MovieRepository;
import com.dekisolutions.movieapp.data.model.Movie;
import com.dekisolutions.movieapp.ui.common.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends BaseViewModel {
	
	private MovieRepository movieRepository;
	private MutableLiveData<List<Movie>> movieLiveData = new MutableLiveData<>();
	
	public SearchViewModel(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
	
	public void search(String query) {
		 addDisposable(movieRepository.getMovies(query).subscribeOn(Schedulers.io()).map(movies -> {
			for (Movie movie : movies) {
				ArrayList<String> genreNames = new ArrayList<>();
				for (Integer id : movie.getGenreIds()) {
					genreNames.add(movieRepository.getGenreName(id));
				}
				movie.setGenreNames(genreNames);
			}
			return movies;
		}).observeOn(AndroidSchedulers.mainThread()).subscribe(movies -> {
			movieLiveData.postValue(movies);
		}));
	}
	
	public LiveData<List<Movie>> getSearchResult() {
		return movieLiveData;
	}
	
	@Override
	protected void onCleared() {
		super.onCleared();
		movieRepository.clearAll();
	}
}
