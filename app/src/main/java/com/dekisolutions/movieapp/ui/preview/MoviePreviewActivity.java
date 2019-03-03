package com.dekisolutions.movieapp.ui.preview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.dekisolutions.movieapp.R;
import com.dekisolutions.movieapp.data.model.Cast;
import com.dekisolutions.movieapp.data.model.Crew;
import com.dekisolutions.movieapp.data.model.Movie;
import com.dekisolutions.movieapp.ui.common.BaseActivity;
import com.dekisolutions.movieapp.util.ImageUtils;
import java.util.List;

public class MoviePreviewActivity extends BaseActivity<MoviePreviewViewModel> {
	
	public static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
	
	private ImageView previewImageView;
	private TextView titleTextView;
	private TextView descTextView;
	private Button favoriteButton;
	private TextView castTextView;
	private TextView crewTextView;
	
	private int movieId;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);
		previewImageView = findViewById(R.id.movie_preview_image);
		titleTextView = findViewById(R.id.movie_preview_title);
		descTextView = findViewById(R.id.movie_preview_desc);
		castTextView = findViewById(R.id.movie_preview_cast);
		crewTextView = findViewById(R.id.movie_preview_crew);
		favoriteButton = findViewById(R.id.movie_preview_favorite);
		
		if (getSupportActionBar() != null) {
			getSupportActionBar().setHomeButtonEnabled(true);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		findViewById(R.id.movie_preview_title);
		observeData();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return true;
	}
	
	private void observeData() {
		getViewModel().getMovie(movieId).observe(this, this::initView);
		getViewModel().getCast().observe(this, this::initCastView);
		getViewModel().getCrew().observe(this, this::initCrewView);
	}
	
	private void initCrewView(List<Crew> crews) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Crew crew : crews) {
			stringBuilder.append(crew.getName()).append(" ");
		}
		crewTextView.setText(stringBuilder.toString());
	}
	
	private void initCastView(List<Cast> casts) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Cast cast : casts) {
			stringBuilder.append(cast.getName()).append(" ");
		}
		castTextView.setText(stringBuilder.toString());
	}
	
	private void initView(Movie movie) {
		if (movie == null) {
			return;
		}
		titleTextView.setText(movie.getTitle());
		Glide.with(this).load(ImageUtils.getPostUrl(movie.getPosterPath())).into(previewImageView);
		descTextView.setText(movie.getOverview());
		favoriteButton.setOnClickListener(view -> {
			Toast.makeText(this, "Added to favorite", Toast.LENGTH_LONG).show();
			getViewModel().addToFavorite(movie.getId());
		});
	}
	
	@Override
	public int provideLayout() {
		return R.layout.activity_movie_preview;
	}
	
	@Override
	public Class<MoviePreviewViewModel> provideViewModel() {
		return MoviePreviewViewModel.class;
	}
}
