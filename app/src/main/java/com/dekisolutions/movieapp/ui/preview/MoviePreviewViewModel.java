package com.dekisolutions.movieapp.ui.preview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.dekisolutions.movieapp.data.MovieRepository;
import com.dekisolutions.movieapp.data.model.Cast;
import com.dekisolutions.movieapp.data.model.Crew;
import com.dekisolutions.movieapp.data.model.Movie;
import com.dekisolutions.movieapp.ui.common.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

;

public class MoviePreviewViewModel extends BaseViewModel {
	
	private final MovieRepository movieRepository;
	private MutableLiveData<Movie> movieMutableLiveData = new MutableLiveData<>();
	private MutableLiveData<List<Crew>> crewLiveData = new MutableLiveData<>();
	private MutableLiveData<List<Cast>> castLiveData = new MutableLiveData<>();
	
	public MoviePreviewViewModel(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
	
	public LiveData<Movie> getMovie(int movieId) {
		init(movieId);
		return movieMutableLiveData;
	}
	
	private void init(int movieId) {
		addDisposable(movieRepository.getMovie(movieId)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(movie -> movieMutableLiveData.postValue(movie)));
		movieRepository.fetchCredits(movieId);
		addDisposable(movieRepository.getCast(movieId)
			.subscribeOn(Schedulers.io())
			.subscribe(casts -> castLiveData.postValue(casts)));
		addDisposable(movieRepository.getCrew(movieId)
			.subscribeOn(Schedulers.io())
			.subscribe(crews -> crewLiveData.postValue(crews)));
	}
	
	public void addToFavorite(Integer movieId) {
		movieRepository.addToFavorite(movieId);
	}
	
	@Override
	protected void onCleared() {
		super.onCleared();
		movieRepository.clearAll();
	}
	
	public LiveData<List<Cast>> getCast() {
		return castLiveData;
	}
	
	public LiveData<List<Crew>> getCrew() {
		return crewLiveData;
	}
}
