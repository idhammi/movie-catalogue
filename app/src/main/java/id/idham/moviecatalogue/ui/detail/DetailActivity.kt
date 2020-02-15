package id.idham.moviecatalogue.ui.detail

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.model.MovieModel
import id.idham.moviecatalogue.model.TvShowModel
import id.idham.moviecatalogue.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.img_photo
import kotlinx.android.synthetic.main.item_movie.txt_name
import java.io.Serializable
import java.util.*

class DetailActivity : BaseActivity() {

    object IntentKey {
        const val ITEM = "DetailActivity.ITEM"
        const val TYPE = "DetailActivity.TYPE"
    }

    private lateinit var movie: MovieModel
    private lateinit var tvShow: TvShowModel

    private val detailType: DetailType
        get() = intent.getSerializableExtra(IntentKey.TYPE) as DetailType

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        if (detailType is DetailType.MOVIE) {
            movie = intent.getParcelableExtra(IntentKey.ITEM) as MovieModel
            setupMovieDisplay()
        } else {
            tvShow = intent.getParcelableExtra(IntentKey.ITEM) as TvShowModel
            setupTvShowDisplay()
        }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getLanguageName(lang: String): String {
        val loc = Locale(lang)
        return loc.getDisplayLanguage(loc)
    }

    sealed class DetailType : Serializable {
        object MOVIE : DetailType()
        object TVSHOW : DetailType()
    }

}
