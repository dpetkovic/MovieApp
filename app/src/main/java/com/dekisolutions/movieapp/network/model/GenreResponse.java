package com.dekisolutions.movieapp.network.model;

import com.dekisolutions.movieapp.data.model.Genre;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GenreResponse {
	
	@SerializedName("genres")
	private List<Genre> genreList;
	
	public List<Genre> getGenreList() {
		return genreList;
	}
	
	public void setGenreList(List<Genre> genreList) {
		this.genreList = genreList;
	}
}
