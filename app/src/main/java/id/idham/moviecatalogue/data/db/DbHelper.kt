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

    fun getMovie(): Single<List<Movie>> {
        return movieDao.getAll()
    }

    fun getMovieList(): List<Movie> {
        return movieDao.getAllList()
    }

    fun getMovieById(movieId: Int): Single<List<Movie>> {
        return movieDao.getById(movieId)
    }

    fun insertMovie(movie: Movie) {
        movieDao.insert(movie)
    }

    fun deleteMovieById(movieId: Int) {
        movieDao.deleteById(movieId)
    }

    fun getTvShow(): Single<List<TvShow>> {
        return tvShowDao.getAll()
    }

    fun getTvShowById(tvShowId: Int): Single<List<TvShow>> {
        return tvShowDao.getById(tvShowId)
    }

    fun insertTvShow(tvShow: TvShow) {
        tvShowDao.insert(tvShow)
    }

    fun deleteTvShowById(tvShowId: Int) {
        tvShowDao.deleteById(tvShowId)
    }

}