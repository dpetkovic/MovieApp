package com.dekisolutions.movieapp.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import com.dekisolutions.movieapp.R;
import com.dekisolutions.movieapp.ui.common.BaseActivity;
import com.dekisolutions.movieapp.ui.common.MovieAdapter;
import com.dekisolutions.movieapp.ui.preview.MoviePreviewActivity;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import java.util.concurrent.TimeUnit;

public class SearchActivity extends BaseActivity<SearchViewModel> {
	
	private static final String TAG = "SearchActivity";
	
	SearchView searchView;
	MovieAdapter movieAdapter;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initToolbar();
		initRecyclerView();
		observeData();
	}
	
	private void observeData() {
		getViewModel().getSearchResult().observe(this, movies -> {
			Log.d(TAG, "size: " + movies.size());
			movieAdapter.submitList(movies);
		});
	}
	
	private void initRecyclerView() {
		RecyclerView recyclerView = findViewById(R.id.search_recycler_view);
		movieAdapter = new MovieAdapter(MovieAdapter.Stat.POPULARITY, movie -> {
			Intent intent = new Intent(this, MoviePreviewActivity.class);
			intent.putExtra(MoviePreviewActivity.EXTRA_MOVIE_ID, movie.getId());
			startActivity(intent);
		});
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setAdapter(movieAdapter);
		DividerItemDecoration dividerItemDecoration =
			new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
		recyclerView.addItemDecoration(dividerItemDecoration);
	}
	
	private void initToolbar() {
		searchView = findViewById(R.id.search_view);
		searchView.onActionViewExpanded();
		addDisposable(RxSearchView.queryTextChanges(searchView)
			.filter(charSequence -> charSequence.length() > 0)
			.debounce(500, TimeUnit.MILLISECONDS)
			.subscribe(charSequence -> {
				getViewModel().search(charSequence.toString());
			}));
		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle("Search Movies");
			getSupportActionBar().setHomeButtonEnabled(true);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return true;
	}
	
	@Override
	public int provideLayout() {
		return R.layout.activity_search;
	}
	
	@Override
	public Class<SearchViewModel> provideViewModel() {
		return SearchViewModel.class;
	}
}
