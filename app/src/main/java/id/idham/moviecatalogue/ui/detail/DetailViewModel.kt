package id.idham.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.idham.moviecatalogue.data.DataManager
import id.idham.moviecatalogue.data.db.entity.Movie
import id.idham.moviecatalogue.data.db.entity.TvShow
import id.idham.moviecatalogue.data.network.response.MovieModel
import id.idham.moviecatalogue.data.network.response.TvShowModel
import id.idham.moviecatalogue.ui.base.BaseViewModel
import id.idham.moviecatalogue.util.ioThread

/**
 * Created by idhammi on 2/16/2020.
 */

class DetailViewModel(private val dataManager: DataManager) : BaseViewModel() {

    private val liveMovieData = MutableLiveData<List<Movie>>()
    private val liveTvShowData = MutableLiveData<List<TvShow>>()

    init {
        liveMovieData.value = listOf()
        liveTvShowData.value = listOf()
    }

    fun observeMovieData(): LiveData<List<Movie>> = liveMovieData
    fun observeTvShowData(): LiveData<List<TvShow>> = liveTvShowData

    fun getMovieData(movieId: Int) {
        dataManager.getFavoriteMovieById(movieId).onResult(
            {
                isError.postValue(null)
                isEmptyData.postValue(it.isEmpty())
                liveMovieData.postValue(it)
            },
            {
                isError.postValue(null)
            }
        )
    }

    fun getTvShowData(tvShowId: Int) {
        dataManager.getFavoriteTvShowById(tvShowId).onResult(
            {
                isEmptyData.postValue(it.isEmpty())
                liveTvShowData.postValue(it)
            },
            {
                isError.postValue(null)
            }
        )
    }

    fun insertMovie(movieModel: MovieModel) =
        ioThread { dataManager.insertFavoriteMovie(Movie.to(movieModel)) }

    fun removeMovieById(movieId: Int) = ioThread { dataManager.deleteFavoriteMovieById(movieId) }

    fun insertTvShow(tvShowModel: TvShowModel) =
        ioThread { dataManager.insertFavoriteTvShow(TvShow.to(tvShowModel)) }

    fun removeTvShowById(tvShowId: Int) = ioThread { dataManager.deleteFavoriteTvShowById(tvShowId) }

}