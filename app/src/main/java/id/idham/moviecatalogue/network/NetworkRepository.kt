package id.idham.moviecatalogue.network

import id.idham.moviecatalogue.BuildConfig
import id.idham.moviecatalogue.model.MovieModel
import id.idham.moviecatalogue.model.TvShowModel
import io.reactivex.Single

/**
 * Created by idhammi on 2/7/2020.
 */

class NetworkRepository(private val service: NetworkService) {

    fun getMovies(language: String): Single<List<MovieModel>> {
        return service.getMovies(BuildConfig.API_KEY, language).map { it.getListResult() }
    }

    fun getTvShows(language: String): Single<List<TvShowModel>> {
        return service.getTvShows(BuildConfig.API_KEY, language).map { it.getListResult() }
    }

}