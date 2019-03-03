package com.dekisolutions.movieapp.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Favorite {
	
	@PrimaryKey
	@NonNull
	private Integer movieId;
	
	public Favorite(Integer movieId) {
		this.movieId = movieId;
	}
	
	public Integer getMovieId() {
		return movieId;
	}
	
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Favorite favorite = (Favorite) o;
		
		return movieId.equals(favorite.movieId);
	}
	
	@Override
	public int hashCode() {
		return movieId.hashCode();
	}
}
