package id.idham.moviecatalogue.data.network

import id.idham.moviecatalogue.BuildConfig
import id.idham.moviecatalogue.data.network.response.MovieModel
import id.idham.moviecatalogue.data.network.response.TvShowModel
import io.reactivex.Single

/**
 * Created by idhammi on 2/7/2020.
 */

class NetworkRepository(private val service: NetworkService) {

    fun getMovies(): Single<List<MovieModel>> {
        return service.getMovies(BuildConfig.API_KEY, BuildConfig.LANG).map { it.getListResult() }
    }

    fun getTvShows(): Single<List<TvShowModel>> {
        return service.getTvShows(BuildConfig.API_KEY, BuildConfig.LANG).map { it.getListResult() }
    }

}