package com.dekisolutions.movieapp.ui.popular;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dekisolutions.movieapp.R;
import com.dekisolutions.movieapp.ui.common.BaseFragment;
import com.dekisolutions.movieapp.ui.common.MovieAdapter;
import com.dekisolutions.movieapp.ui.favorite.FavoriteAdapter;
import com.dekisolutions.movieapp.ui.preview.MoviePreviewActivity;

public class PopularFragment extends BaseFragment<PopularViewModel> {
	
	public static final String TAG = "PopularFragment";
	
	private RecyclerView recyclerView;
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
	
	private void initRecyclerView(View view) {
		recyclerView = view.findViewById(R.id.popular_recycler_view);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
			false);
		movieAdapter = new MovieAdapter(MovieAdapter.Stat.POPULARITY, movie -> {
			Intent intent = new Intent(getContext(), MoviePreviewActivity.class);
			intent.putExtra(MoviePreviewActivity.EXTRA_MOVIE_ID, movie.getId());
			startActivity(intent);
		});
		recyclerView.setAdapter(movieAdapter);
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
			linearLayoutManager.getOrientation());
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.addItemDecoration(dividerItemDecoration);
	}
	
	private void observeData() {
		getViewModel().getPopularMovies().observe(this, movie -> {
			Log.d(TAG, movie != null ? "size: " + movie.size() : "null");
			movieAdapter.submitList(movie);
		});
	}
	
	@Override
	protected int provideLayout() {
		return R.layout.fragment_popular;
	}
	
	@Override
	protected Class<PopularViewModel> provideViewModel() {
		return PopularViewModel.class;
	}
}
