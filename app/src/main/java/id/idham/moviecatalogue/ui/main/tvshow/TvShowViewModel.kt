package id.idham.moviecatalogue.ui.main.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.idham.moviecatalogue.model.TvShowModel
import id.idham.moviecatalogue.network.NetworkRepository
import id.idham.moviecatalogue.ui.base.BaseViewModel

/**
 * Created by idhammi on 2/7/2020.
 */

class TvShowViewModel(private val repository: NetworkRepository) : BaseViewModel() {

    private val liveData = MutableLiveData<List<TvShowModel>>()

    init {
        liveData.value = listOf()
    }

    fun observeData(): LiveData<List<TvShowModel>> = liveData

    fun getData() {
        repository.getTvShows().onResult(
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