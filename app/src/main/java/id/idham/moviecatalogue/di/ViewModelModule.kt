package id.idham.moviecatalogue.di

import id.idham.moviecatalogue.ui.main.movie.MovieViewModel
import id.idham.moviecatalogue.ui.main.tvshow.TvShowViewModel
import org.koin.dsl.module

/**
 * Created by idhammi on 2/7/2020.
 */

val viewModelModule = module {

    single { MovieViewModel(get()) }

    single { TvShowViewModel(get()) }

}