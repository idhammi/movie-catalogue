package id.idham.moviecatalogue.di

import id.idham.moviecatalogue.data.db.AppDatabase
import id.idham.moviecatalogue.data.db.DbHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by idhammi on 2/17/2020.
 */

val databaseModule = module {
    single { AppDatabase.getInstance(androidApplication()) }
    single { get<AppDatabase>().movieDao() }
    single { get<AppDatabase>().tvShowDao() }
    single { DbHelper(get(), get()) }
}