package com.dekisolutions.movieapp.ui.common;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dekisolutions.movieapp.R;
import com.dekisolutions.movieapp.data.model.Movie;
import com.dekisolutions.movieapp.util.ImageUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MovieAdapter extends ListAdapter<Movie, RecyclerView.ViewHolder> {
	
	@IntDef({ Stat.POPULARITY, Stat.RATING })
	@Retention(RetentionPolicy.SOURCE)
	public @interface Stat {
		int POPULARITY = 0;
		int RATING = 1;
	}
	
	protected OnMovieClickListener onMovieClickListener;
	protected @Stat int showStat = 0;
	
	public MovieAdapter(@Stat int showStat, OnMovieClickListener onMovieClickListener) {
		super(DIFF_CALLBACK);
		this.showStat = showStat;
		this.onMovieClickListener = onMovieClickListener;
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		return new MovieViewHolder(
			LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_movie_common, viewGroup, false),
			showStat, onMovieClickListener);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
		if (viewHolder instanceof MovieViewHolder) {
			((MovieViewHolder) viewHolder).setup(getItem(i));
		}
	}
	
	private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
		@Override
		public boolean areItemsTheSame(@NonNull Movie oldUser, @NonNull Movie newUser) {
			// User properties may have changed if reloaded from the DB, but ID is fixed
			return oldUser.getId().equals(newUser.getId());
		}
		
		@Override
		public boolean areContentsTheSame(@NonNull Movie oldUser, @NonNull Movie newUser) {
			// NOTE: if you use equals, your object must properly override Object#equals()
			// Incorrectly returning false here will result in too many animations.
			return oldUser.equals(newUser);
		}
	};
	
	public static class MovieViewHolder extends RecyclerView.ViewHolder {
		
		private ImageView imageView;
		private TextView titleTextView;
		private TextView genreTextView;
		private TextView rating;
		private @Stat int showStat;
		private OnMovieClickListener onMovieClickListener;
		
		public MovieViewHolder(@NonNull View itemView, @Stat int showStat, OnMovieClickListener onMovieClickListener) {
			super(itemView);
			this.imageView = itemView.findViewById(R.id.image_imageView);
			this.titleTextView = itemView.findViewById(R.id.title_textView);
			this.genreTextView = itemView.findViewById(R.id.genre_textView);
			this.rating = itemView.findViewById(R.id.rating_textView);
			this.showStat = showStat;
			this.onMovieClickListener = onMovieClickListener;
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
		}
	}
	
	public interface OnMovieClickListener {
		void onClick(Movie movie);
	}
}
