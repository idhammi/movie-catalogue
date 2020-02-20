package id.idham.moviecatalogue.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.idham.moviecatalogue.data.db.DbRepository
import id.idham.moviecatalogue.data.db.entity.TvShow
import id.idham.moviecatalogue.ui.base.BaseViewModel

/**
 * Created by idhammi on 2/7/2020.
 */

class FavoriteTvShowViewModel(private val repository: DbRepository) : BaseViewModel() {

    private val liveData = MutableLiveData<List<TvShow>>()

    init {
        liveData.value = listOf()
    }

    fun observeData(): LiveData<List<TvShow>> = liveData

    fun getData() {
        repository.getAllTvShow().onResult(
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