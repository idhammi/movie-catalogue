package id.idham.moviecatalogue.di

import id.idham.moviecatalogue.common.DiffCallback
import id.idham.moviecatalogue.data.DataManager
import org.koin.dsl.module

/**
 * Created by idhammi on 2/7/2020.
 */

val applicationModule = module {
    single { DiffCallback() }
    single { DataManager(get(), get()) }
}