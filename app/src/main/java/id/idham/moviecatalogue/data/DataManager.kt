package id.idham.moviecatalogue.data

import id.idham.moviecatalogue.data.db.DbHelper
import id.idham.moviecatalogue.data.db.entity.Movie
import id.idham.moviecatalogue.data.db.entity.TvShow
import id.idham.moviecatalogue.data.network.NetworkHelper
import id.idham.moviecatalogue.data.network.response.MovieModel
import id.idham.moviecatalogue.data.network.response.TvShowModel
import io.reactivex.Single

/**
 * Created by idhammi on 2/22/2020.
 */

class DataManager(private val dbHelper: DbHelper, private val networkHelper: NetworkHelper) {

    fun getMovies(): Single<List<MovieModel>> {
        return networkHelper.getMovies()
    }

    fun getMoviesByQuery(query: String): Single<List<MovieModel>> {
        return networkHelper.getMoviesByQuery(query)
    }

    fun getMoviesByDate(date: String): Single<List<MovieModel>> {
        return networkHelper.getMoviesByDate(date)
    }

    fun getTvShows(): Single<List<TvShowModel>> {
        return networkHelper.getTvShows()
    }

    fun getTvShowsByQuery(query: String): Single<List<TvShowModel>> {
        return networkHelper.getTvShowsByQuery(query)
    }

    fun getFavoriteMovie(): Single<List<Movie>> {
        return dbHelper.getMovie()
    }

    fun getFavoriteMovieList(): List<Movie> {
        return dbHelper.getMovieList()
    }

    fun getFavoriteMovieById(movieId: Int): Single<List<Movie>> {
        return dbHelper.getMovieById(movieId)
    }

    fun insertFavoriteMovie(movie: Movie) {
        dbHelper.insertMovie(movie)
    }

    fun deleteFavoriteMovieById(movieId: Int) {
        dbHelper.deleteMovieById(movieId)
    }

    fun getFavoriteTvShow(): Single<List<TvShow>> {
        return dbHelper.getTvShow()
    }

    fun getFavoriteTvShowById(tvShowId: Int): Single<List<TvShow>> {
        return dbHelper.getTvShowById(tvShowId)
    }

    fun insertFavoriteTvShow(tvShow: TvShow) {
        dbHelper.insertTvShow(tvShow)
    }

    fun deleteFavoriteTvShowById(tvShowId: Int) {
        dbHelper.deleteTvShowById(tvShowId)
    }

}