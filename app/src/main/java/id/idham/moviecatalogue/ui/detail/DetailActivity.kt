package id.idham.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.data.db.entity.Movie
import id.idham.moviecatalogue.data.db.entity.TvShow
import id.idham.moviecatalogue.data.network.response.MovieModel
import id.idham.moviecatalogue.data.network.response.TvShowModel
import id.idham.moviecatalogue.extension.toast
import id.idham.moviecatalogue.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.img_photo
import kotlinx.android.synthetic.main.item_movie.txt_name
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.Serializable
import java.util.*

class DetailActivity : BaseActivity() {

    private val viewModel by viewModel<DetailViewModel>()

    object IntentKey {
        const val ITEM = "DetailActivity.ITEM"
        const val TYPE = "DetailActivity.TYPE"
    }

    sealed class DetailType : Serializable {
        object MOVIE : DetailType()
        object TVSHOW : DetailType()
    }

    private lateinit var movie: MovieModel
    private lateinit var tvShow: TvShowModel

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private val detailType: DetailType
        get() = intent.getSerializableExtra(IntentKey.TYPE) as DetailType

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        observingData()
        if (detailType is DetailType.MOVIE) {
            movie = intent.getParcelableExtra(IntentKey.ITEM) as MovieModel
            setupMovieDisplay()
            viewModel.getMovieData(movie.id)
        } else {
            tvShow = intent.getParcelableExtra(IntentKey.ITEM) as TvShowModel
            setupTvShowDisplay()
            viewModel.getTvShowData(tvShow.id)
        }
    }

    private fun observingData() {
        with(viewModel) {
            observeMovieData().onResult {
                isFavorite = it.isNotEmpty()
                setFavorite()
            }
            observeTvShowData().onResult {
                isFavorite = it.isNotEmpty()
                setFavorite()
            }
            observeEmptyData().onResult {
                isFavorite = false
                setFavorite()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
            }
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupMovieDisplay() {
        Glide.with(this).load(movie.getPosterFullPath(size = 500))
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().placeholder(R.color.colorGrey))
            .apply(RequestOptions().error(R.color.colorGrey))
            .into(img_photo)
        txt_name.text = movie.title
        txt_year.text = movie.getYearReleased()
        txt_rating.text = movie.voteAverage.toString()
        txt_language.text = getLanguageName(movie.originalLanguage)
        txt_detail_description.text = movie.overview
        txt_release_date.text = movie.releaseDate
    }

    private fun setupTvShowDisplay() {
        Glide.with(this).load(tvShow.getPosterFullPath(size = 342))
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().placeholder(R.color.colorGrey))
            .apply(RequestOptions().error(R.color.colorGrey))
            .into(img_photo)
        txt_name.text = tvShow.name
        txt_year.text = tvShow.getYearReleased()
        txt_rating.text = tvShow.voteAverage.toString()
        txt_language.text = getLanguageName(tvShow.originalLanguage)
        txt_detail_description.text = tvShow.overview
        txt_release_date_title.text = getString(R.string.first_air_date)
        txt_release_date.text = tvShow.firstAirDate
    }

    private fun getLanguageName(lang: String): String {
        val loc = Locale(lang)
        return loc.getDisplayLanguage(loc)
    }

    private fun addToFavorite() {
        if (detailType is DetailType.MOVIE) {
            viewModel.insertMovie(convertToMovieEntity(movie))
        } else viewModel.insertTvShow(convertToTvShowEntity(tvShow))
        toast(getString(R.string.added_to_favorite))
    }

    private fun removeFromFavorite() {
        if (detailType is DetailType.MOVIE) {
            viewModel.removeMovieById(movie.id)
        } else viewModel.removeTvShowById(tvShow.id)
        toast(getString(R.string.removed_from_favorite))
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        } else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
    }

    private fun convertToMovieEntity(movieModel: MovieModel): Movie {
        return Movie(
            movieId = movieModel.id,
            posterPath = movieModel.posterPath,
            originalLanguage = movieModel.originalLanguage,
            title = movieModel.title,
            voteAverage = movieModel.voteAverage,
            overview = movieModel.overview,
            releaseDate = movieModel.releaseDate
        )
    }

    private fun convertToTvShowEntity(tvShowModel: TvShowModel): TvShow {
        return TvShow(
            tvShowId = tvShowModel.id,
            posterPath = tvShowModel.posterPath,
            originalLanguage = tvShowModel.originalLanguage,
            name = tvShowModel.name,
            voteAverage = tvShowModel.voteAverage,
            overview = tvShowModel.overview,
            firstAirDate = tvShowModel.firstAirDate
        )
    }

}
