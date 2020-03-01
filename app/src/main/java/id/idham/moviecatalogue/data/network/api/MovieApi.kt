package id.idham.moviecatalogue.data.network.api

import id.idham.moviecatalogue.data.network.response.MovieResponse
import id.idham.moviecatalogue.data.network.response.TvShowResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by idhammi on 2/7/2020.
 */

interface MovieApi {

    @GET("discover/movie")
    fun getMovies(@Query("api_key") apiKey: String, @Query("language") language: String)
            : Single<MovieResponse>

    @GET("search/movie")
    fun getMoviesByQuery(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Single<MovieResponse>

    @GET("discover/movie")
    fun getMoviesByDate(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") dateGte: String,
        @Query("primary_release_date.lte") dateLte: String
    ): Single<MovieResponse>

    @GET("discover/tv")
    fun getTvShows(@Query("api_key") apiKey: String, @Query("language") language: String)
            : Single<TvShowResponse>

    @GET("search/tv")
    fun getTvShowsByQuery(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Single<TvShowResponse>

}