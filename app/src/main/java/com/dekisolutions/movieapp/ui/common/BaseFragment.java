package com.dekisolutions.movieapp.ui.common;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dekisolutions.movieapp.data.MovieRepository;

public abstract class BaseFragment<T extends ViewModel> extends Fragment {
	
	CommonFactory commonFactory;
	private T viewModel;
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		commonFactory = new CommonFactory(new MovieRepository());
		super.onCreate(savedInstanceState);
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {
		viewModel = ViewModelProviders.of(this, commonFactory).get(provideViewModel());
		return inflater.inflate(provideLayout(), container, false);
	}
	
	
	protected abstract int provideLayout();
	
	protected abstract Class<T> provideViewModel();
	
	public T getViewModel() {
		return viewModel;
	}
}
