package id.idham.moviecatalogue.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.idham.moviecatalogue.data.DataManager
import id.idham.moviecatalogue.data.db.entity.Movie
import id.idham.moviecatalogue.ui.base.BaseViewModel

/**
 * Created by idhammi on 2/7/2020.
 */

class FavoriteMovieViewModel(private val dataManager: DataManager) : BaseViewModel() {

    private val liveData = MutableLiveData<List<Movie>>()

    init {
        liveData.value = listOf()
    }

    fun observeData(): LiveData<List<Movie>> = liveData

    fun getData() {
        dataManager.getFavoriteMovie().onResult(
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