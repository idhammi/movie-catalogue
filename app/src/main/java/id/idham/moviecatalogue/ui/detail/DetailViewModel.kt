package id.idham.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.idham.moviecatalogue.data.db.DbRepository
import id.idham.moviecatalogue.data.db.entity.Movie
import id.idham.moviecatalogue.data.db.entity.TvShow
import id.idham.moviecatalogue.data.network.response.MovieModel
import id.idham.moviecatalogue.data.network.response.TvShowModel
import id.idham.moviecatalogue.ui.base.BaseViewModel
import id.idham.moviecatalogue.util.ioThread

/**
 * Created by idhammi on 2/16/2020.
 */

class DetailViewModel(private val repository: DbRepository) : BaseViewModel() {

    private val liveMovieData = MutableLiveData<List<Movie>>()
    private val liveTvShowData = MutableLiveData<List<TvShow>>()

    init {
        liveMovieData.value = listOf()
        liveTvShowData.value = listOf()
    }

    fun observeMovieData(): LiveData<List<Movie>> = liveMovieData
    fun observeTvShowData(): LiveData<List<TvShow>> = liveTvShowData

    fun getMovieData(movieId: Int) {
        repository.getMovieById(movieId).onResult(
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
        repository.getTvShowById(tvShowId).onResult(
            {
                isEmptyData.postValue(it.isEmpty())
                liveTvShowData.postValue(it)
            },
            {
                isError.postValue(null)
            }
        )
    }

    fun insertMovie(movieModel: MovieModel) = ioThread { repository.insertMovie(Movie.to(movieModel)) }

    fun removeMovieById(movieId: Int) = ioThread { repository.deleteMovieById(movieId) }

    fun insertTvShow(tvShowModel: TvShowModel) = ioThread { repository.insertTvShow(TvShow.to(tvShowModel)) }

    fun removeTvShowById(tvShowId: Int) = ioThread { repository.deleteTvShowById(tvShowId) }

}