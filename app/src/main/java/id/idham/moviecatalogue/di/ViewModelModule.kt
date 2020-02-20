package id.idham.moviecatalogue.di

import id.idham.moviecatalogue.ui.detail.DetailViewModel
import id.idham.moviecatalogue.ui.main.favorite.FavoriteMovieViewModel
import id.idham.moviecatalogue.ui.main.favorite.FavoriteTvShowViewModel
import id.idham.moviecatalogue.ui.main.movie.MovieViewModel
import id.idham.moviecatalogue.ui.main.tvshow.TvShowViewModel
import org.koin.dsl.module

/**
 * Created by idhammi on 2/7/2020.
 */

val viewModelModule = module {
    single { MovieViewModel(get()) }
    single { TvShowViewModel(get()) }
    single { DetailViewModel(get()) }
    single { FavoriteMovieViewModel(get()) }
    single { FavoriteTvShowViewModel(get()) }
}