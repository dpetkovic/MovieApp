package com.dekisolutions.movieapp.ui.common;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.dekisolutions.movieapp.data.MovieRepository;
import java.lang.reflect.InvocationTargetException;

public class CommonFactory implements ViewModelProvider.Factory {
	
	private MovieRepository repository;
	
	public CommonFactory(MovieRepository repository) {
		this.repository = repository;
	}
	
	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		try {
			return modelClass.getConstructor(repository.getClass()).newInstance(repository);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("unable to create view model");
	}
}
