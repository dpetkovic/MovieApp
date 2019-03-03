package com.dekisolutions.movieapp.ui.top;

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
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TopViewModelTest {
	
	@Mock
	MovieRepository movieRepository;
	
	@Rule
	public TrampolineSchedulerRule rule = new TrampolineSchedulerRule();
	
	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
	
	
	@Test
	public void getMoviesByRating() {
		when(movieRepository.getMoviesByRating()).thenReturn(Flowable.just(Collections.nCopies(10, new Movie())));
		
		TopViewModel topViewModel = new TopViewModel(movieRepository);
		topViewModel.getMoviesByRating().observeForever(movies -> {
			Assert.assertEquals(movies.size(), 10 );
		});
	}
}