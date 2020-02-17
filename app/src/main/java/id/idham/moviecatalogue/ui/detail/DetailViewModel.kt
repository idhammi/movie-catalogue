package id.idham.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.idham.moviecatalogue.data.db.DbRepository
import id.idham.moviecatalogue.data.db.entity.Movie
import id.idham.moviecatalogue.data.db.entity.TvShow
import id.idham.moviecatalogue.ui.base.BaseViewModel
import id.idham.moviecatalogue.util.ioThread

/**
 * Created by idhammi on 2/16/2020.
 */

class DetailViewModel(private val dbRepository: DbRepository) : BaseViewModel() {

    private val liveMovieData = MutableLiveData<List<Movie>>()
    private val liveTvShowData = MutableLiveData<List<TvShow>>()

    init {
        liveMovieData.value = listOf()
        liveTvShowData.value = listOf()
    }

    fun observeMovieData(): LiveData<List<Movie>> = liveMovieData
    fun observeTvShowData(): LiveData<List<TvShow>> = liveTvShowData

    fun getMovieData(movieId: Int) {
        dbRepository.getMovieById(movieId).onResult(
            {
                isEmptyData.postValue(it.isEmpty())
                liveMovieData.postValue(it)
            },
            {
                isError.postValue(null)
            }
        )
    }

    fun getTvShowData(tvShowId: Int) {
        dbRepository.getTvShowById(tvShowId).onResult(
            {
                isEmptyData.postValue(it.isEmpty())
                liveTvShowData.postValue(it)
            },
            {
                isError.postValue(null)
            }
        )
    }

    fun insertMovie(movie: Movie) = ioThread { dbRepository.insertMovie(movie) }

    fun removeMovieById(movieId: Int) = ioThread { dbRepository.deleteMovieById(movieId) }

    fun insertTvShow(tvShow: TvShow) = ioThread { dbRepository.insertTvShow(tvShow) }

    fun removeTvShowById(tvShowId: Int) = ioThread { dbRepository.deleteTvShowById(tvShowId) }

}