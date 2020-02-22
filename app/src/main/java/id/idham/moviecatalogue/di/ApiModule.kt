package id.idham.moviecatalogue.di

import id.idham.moviecatalogue.data.network.api.MovieApi
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by idhammi on 2/22/2020.
 */

val apiModule = module {
    single { get<Retrofit>().create(MovieApi::class.java) }
}