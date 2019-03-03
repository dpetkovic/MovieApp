package com.dekisolutions.movieapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.dekisolutions.movieapp.R;
import com.dekisolutions.movieapp.ui.favorite.FavoriteFragment;
import com.dekisolutions.movieapp.ui.popular.PopularFragment;
import com.dekisolutions.movieapp.ui.search.SearchActivity;
import com.dekisolutions.movieapp.ui.top.TopFragment;

public class HomeActivity extends AppCompatActivity {
	
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
		
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_popular:
					showPopular();
					return true;
				case R.id.navigation_top:
					showTop();
					return true;
				case R.id.navigation_favorite:
					showFavorite();
					return true;
			}
			return false;
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.toolbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_search) {
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
		}
		return true;
	}
	
	private void showFavorite() {
		FavoriteFragment favoriteFragment = new FavoriteFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, favoriteFragment).commit();
	}
	
	private void showTop() {
		TopFragment topFragment = new TopFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, topFragment, TopFragment.TAG).commit();
	}
	
	private void showPopular() {
		PopularFragment popularFragment = new PopularFragment();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, popularFragment, PopularFragment.TAG).commit();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		showPopular();
		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
	}
}
