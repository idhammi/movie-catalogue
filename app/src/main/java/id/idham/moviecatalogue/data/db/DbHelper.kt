package id.idham.moviecatalogue.data.db

import id.idham.moviecatalogue.data.db.dao.MovieDao
import id.idham.moviecatalogue.data.db.dao.TvShowDao
import id.idham.moviecatalogue.data.db.entity.Movie
import id.idham.moviecatalogue.data.db.entity.TvShow
import io.reactivex.Single

/**
 * Created by idhammi on 2/7/2020.
 */

class DbHelper(private val movieDao: MovieDao, private val tvShowDao: TvShowDao) {

    fun getAllMovie(): Single<List<Movie>> {
        return movieDao.getAll()
    }

    fun getMovieById(movieId: Int): Single<List<Movie>> {
        return movieDao.getById(movieId)
    }

    fun insertMovie(movie: Movie) {
        movieDao.insert(movie)
    }

    fun deleteMovie(movie: Movie) {
        movieDao.delete(movie)
    }

    fun deleteMovieById(movieId: Int) {
        movieDao.deleteById(movieId)
    }

    fun getAllTvShow(): Single<List<TvShow>> {
        return tvShowDao.getAll()
    }

    fun getTvShowById(tvShowId: Int): Single<List<TvShow>> {
        return tvShowDao.getById(tvShowId)
    }

    fun insertTvShow(tvShow: TvShow) {
        tvShowDao.insert(tvShow)
    }

    fun deleteTvShow(tvShow: TvShow) {
        tvShowDao.delete(tvShow)
    }

    fun deleteTvShowById(tvShowId: Int) {
        tvShowDao.deleteById(tvShowId)
    }

}