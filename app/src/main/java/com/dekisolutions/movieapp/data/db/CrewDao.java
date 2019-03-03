package com.dekisolutions.movieapp.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.dekisolutions.movieapp.data.model.Crew;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface CrewDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<Crew> crewList);
	
	@Query("SELECT * FROM Crew WHERE movieId = :movieId LIMIT 10")
	Flowable<List<Crew>> getCrew(int movieId);
}
