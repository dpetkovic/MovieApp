package com.dekisolutions.movieapp.ui.favorite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dekisolutions.movieapp.R;
import com.dekisolutions.movieapp.data.model.Movie;
import com.dekisolutions.movieapp.ui.common.MovieAdapter;
import com.dekisolutions.movieapp.util.ImageUtils;

public class FavoriteAdapter extends MovieAdapter {
	
	private final OnRemoveFromFavoriteListener onRemoveFromFavoriteListener;
	
	public FavoriteAdapter(int showStat, OnMovieClickListener onMovieClickListener, OnRemoveFromFavoriteListener onRemoveFromFavoriteListener) {
		super(showStat, onMovieClickListener);
		this.onRemoveFromFavoriteListener = onRemoveFromFavoriteListener;
	}
	
	@NonNull
	@Override
	public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		return new FavoriteViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_favorite,
			viewGroup, false), showStat, onMovieClickListener, onRemoveFromFavoriteListener);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
		if (viewHolder instanceof FavoriteViewHolder) {
			((FavoriteViewHolder) viewHolder).setup(getItem(i));
		}
	}
	
	private class FavoriteViewHolder extends RecyclerView.ViewHolder {
		
		private ImageButton removeFavorite;
		private ImageView imageView;
		private TextView titleTextView;
		private TextView genreTextView;
		private TextView rating;
		private @Stat int showStat;
		private OnMovieClickListener onMovieClickListener;
		private OnRemoveFromFavoriteListener onRemoveFromFavoriteListener;
		
		public FavoriteViewHolder(@NonNull View itemView, @Stat int showStat,
			OnMovieClickListener onMovieClickListener, OnRemoveFromFavoriteListener onRemoveFromFavoriteListener) {
			super(itemView);
			this.imageView = itemView.findViewById(R.id.image_imageView);
			this.titleTextView = itemView.findViewById(R.id.title_textView);
			this.genreTextView = itemView.findViewById(R.id.genre_textView);
			this.rating = itemView.findViewById(R.id.rating_textView);
			this.removeFavorite = itemView.findViewById(R.id.favorite_remove_button);
			this.showStat = showStat;
			this.onMovieClickListener = onMovieClickListener;
			this.onRemoveFromFavoriteListener = onRemoveFromFavoriteListener;
		}
		
		public void setup(Movie movie) {
			Glide.with(itemView).load(ImageUtils.getPostUrl(movie.getPosterPath())).centerCrop().into(imageView);
			titleTextView.setText(movie.getOriginalTitle());
			genreTextView.setText(movie.getGenreNames().toString());
			switch (showStat) {
				case Stat.POPULARITY:
					rating.setText(String.valueOf(movie.getPopularity()));
					break;
				case Stat.RATING:
					rating.setText(String.valueOf(movie.getVoteAverage()));
					break;
			}
			itemView.setOnClickListener(view -> {
				onMovieClickListener.onClick(movie);
			});
			removeFavorite.setOnClickListener(view -> {
				onRemoveFromFavoriteListener.onRemove(movie.getId());
			});
		}
	}
	
	public interface OnRemoveFromFavoriteListener {
		void onRemove(int movieId);
	}
}
