package id.idham.moviecatalogue.data.network

import id.idham.moviecatalogue.BuildConfig
import id.idham.moviecatalogue.data.network.api.MovieApi
import id.idham.moviecatalogue.data.network.response.MovieModel
import id.idham.moviecatalogue.data.network.response.TvShowModel
import io.reactivex.Single

/**
 * Created by idhammi on 2/7/2020.
 */

class NetworkHelper(private val movieApi: MovieApi) {

    fun getMovies(): Single<List<MovieModel>> {
        return movieApi.getMovies(BuildConfig.API_KEY, BuildConfig.LANG).map { it.getListResult() }
    }

    fun getMoviesByQuery(query: String): Single<List<MovieModel>> {
        return movieApi.getMoviesByQuery(BuildConfig.API_KEY, BuildConfig.LANG, query)
            .map { it.getListResult() }
    }

    fun getMoviesByDate(date: String): Single<List<MovieModel>> {
        return movieApi.getMoviesByDate(BuildConfig.API_KEY, date, date).map { it.getListResult() }
    }

    fun getTvShows(): Single<List<TvShowModel>> {
        return movieApi.getTvShows(BuildConfig.API_KEY, BuildConfig.LANG).map { it.getListResult() }
    }

    fun getTvShowsByQuery(query: String): Single<List<TvShowModel>> {
        return movieApi.getTvShowsByQuery(BuildConfig.API_KEY, BuildConfig.LANG, query)
            .map { it.getListResult() }
    }

}