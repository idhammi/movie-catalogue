package id.idham.moviecatalogue.di

import id.idham.moviecatalogue.BuildConfig
import id.idham.moviecatalogue.common.ConnectionLiveData
import id.idham.moviecatalogue.data.network.NetworkRepository
import id.idham.moviecatalogue.data.network.NetworkService
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by idhammi on 2/7/2020.
 */

val networkModule = module {

    single {
        val timeOut = 60L
        val client = OkHttpClient.Builder()
        client.connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            client.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).addInterceptor { chain ->
                val req = chain.request()
                    .newBuilder()
                    .build()
                return@addInterceptor chain.proceed(req)
            }
        }

        return@single client.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    single { createApiService<NetworkService>(get()) }

    single { NetworkRepository(get()) }

    single { ConnectionLiveData(get()) }

}

inline fun <reified T> createApiService(retrofit: Retrofit): T = retrofit.create(T::class.java)