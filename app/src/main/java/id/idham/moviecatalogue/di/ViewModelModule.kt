package id.idham.moviecatalogue.di

import id.idham.moviecatalogue.ui.detail.DetailViewModel
import id.idham.moviecatalogue.ui.main.movie.MovieViewModel
import id.idham.moviecatalogue.ui.main.tvshow.TvShowViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by idhammi on 2/7/2020.
 */

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}