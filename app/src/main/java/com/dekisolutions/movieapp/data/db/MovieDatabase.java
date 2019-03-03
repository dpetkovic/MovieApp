package com.dekisolutions.movieapp.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.dekisolutions.movieapp.data.model.Cast;
import com.dekisolutions.movieapp.data.model.Crew;
import com.dekisolutions.movieapp.data.model.Favorite;
import com.dekisolutions.movieapp.data.model.Genre;
import com.dekisolutions.movieapp.data.model.Movie;

@Database(entities = { Movie.class, Genre.class, Favorite.class, Cast.class, Crew.class}, version = 4)
public abstract class MovieDatabase extends RoomDatabase {
	
	public abstract MoviesDao MoviesDao();
	
	public abstract GenreDao genreDao();
	
	public abstract FavoriteDao favoriteDao();
	
	public abstract CastDao castDao();
	
	public abstract  CrewDao crewDao();
}
