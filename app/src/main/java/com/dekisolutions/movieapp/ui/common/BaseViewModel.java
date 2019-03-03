package com.dekisolutions.movieapp.ui.common;

import android.arch.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {
	
	private CompositeDisposable compositeDisposable = new CompositeDisposable();
	
	public void addDisposable(Disposable disposable) {
		compositeDisposable.add(disposable);
	}
	
	public void clearAll() {
		compositeDisposable.clear();
	}
}
