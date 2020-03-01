package id.idham.moviecatalogue.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.idham.moviecatalogue.data.DataManager
import id.idham.moviecatalogue.data.network.response.MovieModel
import id.idham.moviecatalogue.data.network.response.TvShowModel
import id.idham.moviecatalogue.ui.base.BaseViewModel
import id.idham.moviecatalogue.util.toSingleEvent

/**
 * Created by idhammi on 2/25/2020.
 */

class SearchViewModel(private val dataManager: DataManager) : BaseViewModel() {

    private val liveDataMovie = MutableLiveData<List<MovieModel>>()
    private val singleDataMovie = liveDataMovie.toSingleEvent()

    private val liveDataTvShow = MutableLiveData<List<TvShowModel>>()
    private val singleDataTvShow = liveDataTvShow.toSingleEvent()

    init {
        liveDataMovie.value = listOf()
        liveDataTvShow.value = listOf()
    }

    fun observeMovie(): LiveData<List<MovieModel>> = singleDataMovie
    fun observeTvShow(): LiveData<List<TvShowModel>> = singleDataTvShow

    fun getMovie(query: String) {
        dataManager.getMoviesByQuery(query).onResult(
            {
                isError.postValue(null)
                isEmptyData.postValue(it.isEmpty())
                liveDataMovie.postValue(it)
            },
            {
                isError.postValue(null)
            }
        )
    }

    fun getTvShow(query: String) {
        dataManager.getTvShowsByQuery(query).onResult(
            {
                isError.postValue(null)
                isEmptyData.postValue(it.isEmpty())
                liveDataTvShow.postValue(it)
            },
            {
                isError.postValue(null)
            }
        )
    }

}