package id.idham.moviecatalogue

import android.app.Application
import id.idham.moviecatalogue.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by idhammi on 2/7/2020.
 */

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(
                listOf(
                    applicationModule,
                    databaseModule,
                    apiModule,
                    networkModule,
                    viewModelModule
                )
            )
        }
    }

}