package id.idham.moviecatalogue.di

import id.idham.moviecatalogue.common.DiffCallback
import org.koin.dsl.module

/**
 * Created by idhammi on 2/7/2020.
 */

val applicationModule = module {
    single { DiffCallback() }
}