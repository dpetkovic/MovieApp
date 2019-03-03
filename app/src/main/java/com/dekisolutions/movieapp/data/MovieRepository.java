package com.dekisolutions.movieapp.data;

import android.util.Log;
import com.dekisolutions.movieapp.MovieApplication;
import com.dekisolutions.movieapp.data.db.CastDao;
import com.dekisolutions.movieapp.data.db.CrewDao;
import com.dekisolutions.movieapp.data.db.FavoriteDao;
import com.dekisolutions.movieapp.data.db.GenreDao;
import com.dekisolutions.movieapp.data.db.MoviesDao;
import com.dekisolutions.movieapp.data.model.Cast;
import com.dekisolutions.movieapp.data.model.Crew;
import com.dekisolutions.movieapp.data.model.Favorite;
import com.dekisolutions.movieapp.data.model.Movie;
import com.dekisolutions.movieapp.network.NetworkService;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class MovieRepository extends BaseRepository {
	
	private static final String TAG = "MovieRepository";
	
	private final MoviesDao moviesDao;
	private final GenreDao genreDao;
	private final FavoriteDao favoriteDao;
	private final CastDao castDao;
	private final CrewDao crewDao;
	private NetworkService networkService;
	
	public MovieRepository() {
		networkService = new NetworkService();
		moviesDao = MovieApplication.getAppDatabase().MoviesDao();
		genreDao = MovieApplication.getAppDatabase().genreDao();
		favoriteDao = MovieApplication.getAppDatabase().favoriteDao();
		castDao = MovieApplication.getAppDatabase().castDao();
		crewDao = MovieApplication.getAppDatabase().crewDao();
	}
	
	public void fetchMovies() {
		addDisposable(Single.just(0).subscribeOn(Schedulers.io()).subscribe(count -> {
			if (genreDao.getCount() == 0) {
				fetchGenres();
			} else {
				networkService.getMovies().subscribeOn(Schedulers.io()).subscribe(response -> {
					Log.d(TAG, response.toString());
					moviesDao.insert(response.getMovies());
				}, throwable -> Log.d(TAG, throwable.getMessage()));
			}
		}));
	}
	
	private void fetchGenres() {
		addDisposable(networkService.getGenre()
			.subscribeOn(Schedulers.io())
			.flatMap(genres -> Single.just(genreDao.insertAll(genres.getGenreList())))
			.subscribe(integer -> fetchMovies(), throwable -> Log.d(TAG, throwable.getMessage())));
	}
	
	public void fetchCredits(int movieId) {
		addDisposable(networkService.getCredits(movieId).subscribeOn(Schedulers.io()).subscribe(creditsResponse -> {
			List<Crew> crewList = creditsResponse.getCrew();
			for (Crew crew : crewList) {
				crew.setMovieId(movieId);
			}
			crewDao.insertAll(crewList);
			List<Cast> castList = creditsResponse.getCast();
			for (Cast cast : castList) {
				cast.setMovieId(movieId);
			}
			castDao.insertAll(castList);
		}, throwable -> Log.d(TAG, throwable.getMessage())));
	}
	
	public Flowable<List<Cast>> getCast(int movieId) {
		return castDao.getCast(movieId);
	}
	
	public Flowable<List<Crew>> getCrew(int movieId) {
		return crewDao.getCrew(movieId);
	}
	
	public Flowable<List<Movie>> getMoviesByPopularity() {
		return moviesDao.getMoviesByPopularity();
	}
	
	public Flowable<List<Movie>> getMoviesByRating() {
		return moviesDao.getMoviesByRating().subscribeOn(Schedulers.io());
	}
	
	public Flowable<Movie> getMovie(int movieId) {
		return moviesDao.getMovie(movieId);
	}
	
	public Single<List<Movie>> getMovies(String query) {
		return moviesDao.getMovies("%" + query + "%");
	}
	
	public String getGenreName(int genreId) {
		return genreDao.get(genreId);
	}
	
	public Flowable<List<Movie>> getMoviesByFavorite() {
		return favoriteDao.getFavorites().observeOn(Schedulers.io()).switchMap(moviesDao::getMoviesByFavorite);
	}
	
	public void addToFavorite(Integer movieId) {
		addDisposable(Single.just(0).subscribeOn(Schedulers.io()).subscribe(integer -> {
			favoriteDao.insert(new Favorite(movieId));
		}));
	}
	
	public void removeFavorite(int movieId) {
		addDisposable(Single.just(0).subscribeOn(Schedulers.io()).subscribe(integer -> {
			favoriteDao.delete(new Favorite(movieId));
		}));
	}
}
