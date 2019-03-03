package com.dekisolutions.movieapp.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.dekisolutions.movieapp.data.model.Favorite;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface FavoriteDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	long insert(Favorite favorite);
	
	@Query("SELECT movieId FROM Favorite")
	Flowable<List<Integer>> getFavorites();
	
	@Delete
	int delete(Favorite favorite);
}
