package com.dekisolutions.movieapp.ui.popular;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import com.dekisolutions.movieapp.TrampolineSchedulerRule;
import com.dekisolutions.movieapp.data.MovieRepository;
import com.dekisolutions.movieapp.data.model.Movie;
import io.reactivex.Flowable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PopularViewModelTest {
	
	@Mock
	MovieRepository movieRepository;
	
	@Rule
	public TrampolineSchedulerRule rule = new TrampolineSchedulerRule();
	
	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
	
	
	@Before
	public void setup() {
		RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
	}
	
	@Test
	public void getPopularMovies() {
		when(movieRepository.getMoviesByPopularity()).thenReturn(Flowable.just(Collections.nCopies(10, new Movie())));
		PopularViewModel popularViewModel = new PopularViewModel(movieRepository);
		
		popularViewModel.getPopularMovies().observeForever(movies -> {
			Assert.assertEquals(10, movies.size());
		});
	}
}