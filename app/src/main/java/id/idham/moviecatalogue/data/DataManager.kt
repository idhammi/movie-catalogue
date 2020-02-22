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

    fun getAllMovie(): Single<List<Movie>> {
        return dbHelper.getAllMovie()
    }

    fun getMovieById(movieId: Int): Single<List<Movie>> {
        return dbHelper.getMovieById(movieId)
    }

    fun insertMovie(movie: Movie) {
        dbHelper.insertMovie(movie)
    }

    fun deleteMovie(movie: Movie) {
        dbHelper.deleteMovie(movie)
    }

    fun deleteMovieById(movieId: Int) {
        dbHelper.deleteMovieById(movieId)
    }

    fun getAllTvShow(): Single<List<TvShow>> {
        return dbHelper.getAllTvShow()
    }

    fun getTvShowById(tvShowId: Int): Single<List<TvShow>> {
        return dbHelper.getTvShowById(tvShowId)
    }

    fun insertTvShow(tvShow: TvShow) {
        dbHelper.insertTvShow(tvShow)
    }

    fun deleteTvShow(tvShow: TvShow) {
        dbHelper.deleteTvShow(tvShow)
    }

    fun deleteTvShowById(tvShowId: Int) {
        dbHelper.deleteTvShowById(tvShowId)
    }

    fun getMovies(): Single<List<MovieModel>> {
        return networkHelper.getMovies()
    }

    fun getTvShows(): Single<List<TvShowModel>> {
        return networkHelper.getTvShows()
    }

}