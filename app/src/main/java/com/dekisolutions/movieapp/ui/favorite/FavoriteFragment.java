package com.dekisolutions.movieapp.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dekisolutions.movieapp.R;
import com.dekisolutions.movieapp.ui.common.BaseFragment;
import com.dekisolutions.movieapp.ui.common.MovieAdapter;
import com.dekisolutions.movieapp.ui.preview.MoviePreviewActivity;

public class FavoriteFragment extends BaseFragment<FavoriteViewModel> {
	
	private MovieAdapter movieAdapter;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		initRecyclerView(view);
		observeData();
		return view;
	}
	
	private void observeData() {
		getViewModel().getMoviesByFavorite().observe(this, movies -> movieAdapter.submitList(movies));
	}
	
	private void initRecyclerView(View view) {
		RecyclerView recyclerView = view.findViewById(R.id.favorite_recycler_view);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
			false);
		movieAdapter = new FavoriteAdapter(MovieAdapter.Stat.RATING, movie -> {
			Intent intent = new Intent(getContext(), MoviePreviewActivity.class);
			intent.putExtra(MoviePreviewActivity.EXTRA_MOVIE_ID, movie.getId());
			startActivity(intent);
		}, movieId -> {
			getViewModel().removeFavorite(movieId);
		});
		recyclerView.setAdapter(movieAdapter);
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
			linearLayoutManager.getOrientation());
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.addItemDecoration(dividerItemDecoration);
	}
	
	@Override
	protected int provideLayout() {
		return R.layout.fragment_favorite;
	}
	
	@Override
	protected Class<FavoriteViewModel> provideViewModel() {
		return FavoriteViewModel.class;
	}
}
