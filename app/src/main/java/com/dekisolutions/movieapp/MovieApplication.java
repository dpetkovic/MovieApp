package com.dekisolutions.movieapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import com.dekisolutions.movieapp.data.db.MovieDatabase;

public class MovieApplication extends Application {
	
	private static final String DB_NAME = "mydb";
	
	private static MovieDatabase db;
	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		db = Room.databaseBuilder(this, MovieDatabase.class, DB_NAME)
			.fallbackToDestructiveMigration()
			.build();
	}
	
	public static MovieDatabase getAppDatabase() {
		return db;
	}
	
	public Context getAppContext() {
		return context;
	}
}
