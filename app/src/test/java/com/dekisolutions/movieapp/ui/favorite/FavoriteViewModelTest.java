package com.dekisolutions.movieapp.ui.favorite;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import com.dekisolutions.movieapp.TrampolineSchedulerRule;
import com.dekisolutions.movieapp.data.MovieRepository;
import com.dekisolutions.movieapp.data.model.Movie;
import io.reactivex.Flowable;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FavoriteViewModelTest {
	
	@Mock
	MovieRepository movieRepository;
	
	@Rule
	public TrampolineSchedulerRule rule = new TrampolineSchedulerRule();
	
	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
	
	@Test
	public void getMoviesByFavorite() {
		// setup
		when(movieRepository.getMoviesByFavorite()).thenReturn(Flowable.just(Collections.nCopies(10, new Movie())));
		FavoriteViewModel favoriteViewModel = new FavoriteViewModel(movieRepository);
		//test
		favoriteViewModel.getMoviesByFavorite().observeForever(movies -> Assert.assertEquals(10, movies.size()));
	}
}