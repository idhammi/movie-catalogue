package id.idham.moviefavorite.ui.detail

import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.idham.moviefavorite.R
import id.idham.moviefavorite.data.entity.Movie
import id.idham.moviefavorite.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.img_photo
import kotlinx.android.synthetic.main.item_movie.txt_name
import java.util.*

class DetailActivity : BaseActivity() {

    object IntentKey {
        const val ITEM = "DetailActivity.ITEM"
    }

    private val movie: Movie by lazy {
        intent.getParcelableExtra(IntentKey.ITEM) as Movie
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupMovieDisplay()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getLanguageName(lang: String): String {
        val loc = Locale(lang)
        return loc.getDisplayLanguage(loc)
    }

}
