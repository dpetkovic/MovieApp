package com.dekisolutions.movieapp.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.dekisolutions.movieapp.data.model.Cast;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface CastDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<Cast> castList);
	
	@Query("SELECT * FROM `Cast` WHERE movieId = :movieId LIMIT 10")
	Flowable<List<Cast>> getCast(int movieId);
}
