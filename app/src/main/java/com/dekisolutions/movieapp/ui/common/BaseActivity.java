package com.dekisolutions.movieapp.ui.common;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.dekisolutions.movieapp.data.MovieRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity<T extends ViewModel> extends AppCompatActivity {
	
	T viewModel;
	CompositeDisposable compositeDisposable = new CompositeDisposable();
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(provideLayout());
		viewModel = ViewModelProviders.of(this, new CommonFactory(new MovieRepository())).get(provideViewModel());
	}
	
	public T getViewModel() {
		return viewModel;
	}
	
	public abstract int provideLayout();
	
	public abstract Class<T> provideViewModel();
	
	public void addDisposable(Disposable disposable) {
		compositeDisposable.add(disposable);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		compositeDisposable.clear();
	}
}
