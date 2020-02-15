package id.idham.moviecatalogue.ui.main.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.idham.moviecatalogue.model.MovieModel
import id.idham.moviecatalogue.network.NetworkRepository
import id.idham.moviecatalogue.ui.base.BaseViewModel

/**
 * Created by idhammi on 2/7/2020.
 */

class MovieViewModel(private val repository: NetworkRepository) : BaseViewModel() {

    private val liveData = MutableLiveData<List<MovieModel>>()

    init {
        liveData.value = listOf()
    }

    fun observeData(): LiveData<List<MovieModel>> = liveData

    fun getData(language: String) {
        repository.getMovies(language).onResult(
            {
                isError.postValue(null)
                isEmptyData.postValue(it.isEmpty())
                liveData.postValue(it)
            },
            {
                isError.postValue(null)
            }
        )
    }

}