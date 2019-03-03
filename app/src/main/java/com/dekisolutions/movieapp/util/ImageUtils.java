package com.dekisolutions.movieapp.util;

public class ImageUtils {
	
	private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500/";
	
	public static String getPostUrl(String imagePath) {
		return BASE_POSTER_URL + imagePath;
	}
}
