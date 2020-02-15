package id.idham.moviecatalogue.network

import id.idham.moviecatalogue.model.MovieResponse
import id.idham.moviecatalogue.model.TvShowResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by idhammi on 2/7/2020.
 */

interface NetworkService {

    @GET("discover/movie")
    fun getMovies(@Query("api_key") apiKey: String, @Query("language") language: String)
            : Single<MovieResponse>

    @GET("discover/tv")
    fun getTvShows(@Query("api_key") apiKey: String, @Query("language") language: String)
            : Single<TvShowResponse>

}