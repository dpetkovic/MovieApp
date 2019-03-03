package com.dekisolutions.movieapp.data;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseRepository {
	private CompositeDisposable compositeDisposable = new CompositeDisposable();
	
	public void addDisposable(Disposable disposable) {
		compositeDisposable.add(disposable);
	}
	
	public void clearAll() {
		compositeDisposable.clear();
	}
}
