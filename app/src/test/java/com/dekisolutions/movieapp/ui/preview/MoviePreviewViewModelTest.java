package com.dekisolutions.movieapp.ui.preview;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import com.dekisolutions.movieapp.TrampolineSchedulerRule;
import com.dekisolutions.movieapp.data.MovieRepository;
import com.dekisolutions.movieapp.data.model.Movie;
import io.reactivex.Flowable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.Collections;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoviePreviewViewModelTest {
	
	@Mock
	MovieRepository movieRepository;
	
	@Rule
	public TrampolineSchedulerRule rule = new TrampolineSchedulerRule();
	
	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
	
	
	@BeforeClass
	public static void setup() {
		RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> Schedulers.trampoline());
		RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
	}
	
	@Test
	public void getMovie() {
		Movie movie = new Movie();
		movie.setTitle("Zikina Dinastija");
	 	when(movieRepository.getMovie(anyInt())).thenReturn(Flowable.just(movie));
	 	when(movieRepository.getCast(anyInt())).thenReturn(Flowable.just(Collections.emptyList()));
		when(movieRepository.getCrew(anyInt())).thenReturn(Flowable.just(Collections.emptyList()));
	 	doNothing().when(movieRepository).fetchCredits(anyInt());
	 	
	 	MoviePreviewViewModel moviePreviewViewModel = new MoviePreviewViewModel(movieRepository);
	 	moviePreviewViewModel.getMovie(1).observeForever(m -> {
	 		Assert.assertEquals(m.getTitle(), "Zikina Dinastija");
		});
	}
}