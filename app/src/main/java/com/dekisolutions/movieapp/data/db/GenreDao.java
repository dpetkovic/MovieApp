package com.dekisolutions.movieapp.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.dekisolutions.movieapp.data.model.Genre;
import java.util.List;

@Dao
public interface GenreDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	long[] insertAll(List<Genre> genreList);
	
	@Query("SELECT * FROM Genre")
	List<Genre> getAll();
	
	@Query("SELECT name FROM Genre where id = :genreId")
	String get(int genreId);
	
	@Query("SELECT COUNT(*) FROM Genre")
	int getCount();
}
