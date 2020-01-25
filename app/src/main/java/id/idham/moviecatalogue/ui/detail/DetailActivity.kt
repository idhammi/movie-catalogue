package id.idham.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.model.MovieModel
import id.idham.moviecatalogue.model.TvshowModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.img_photo
import kotlinx.android.synthetic.main.item_movie.txt_name
import java.io.Serializable

class DetailActivity : AppCompatActivity() {

    object IntentKey {
        const val ITEM = "DetailActivity.ITEM"
        const val TYPE = "DetailActivity.TYPE"
    }

    private lateinit var movie: MovieModel
    private lateinit var tvshow: TvshowModel

    private val detailType: DetailType
        get() = intent.getSerializableExtra(IntentKey.TYPE) as DetailType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (detailType is DetailType.MOVIE) {
            movie = intent.getParcelableExtra(IntentKey.ITEM) as MovieModel
            setupMovieDisplay()
        } else {
            tvshow = intent.getParcelableExtra(IntentKey.ITEM) as TvshowModel
            setupTvshowDisplay()
        }

    }

    private fun setupMovieDisplay() {
        Glide.with(this).load(movie.photo).into(img_photo)
        txt_name.text = movie.name
        val yearRating = resources.getString(R.string.year_rating, movie.year, movie.rating)
        txt_year_rating.text = yearRating
        txt_director_name.text = movie.director
        txt_screenplay_name.text = movie.screenplay
        Glide.with(this).load(movie.castPhoto1).into(img_cast_1)
        Glide.with(this).load(movie.castPhoto2).into(img_cast_2)
        Glide.with(this).load(movie.castPhoto3).into(img_cast_3)
        txt_cast_1.text = movie.cast1
        txt_cast_2.text = movie.cast2
        txt_cast_3.text = movie.cast3
        txt_detail_description.text = movie.description
        txt_language.text = movie.language
        txt_runtime.text = movie.runtime
    }

    private fun setupTvshowDisplay() {
        Glide.with(this).load(tvshow.photo).into(img_photo)
        txt_name.text = tvshow.name
        val yearRating = resources.getString(R.string.year_rating, tvshow.year, tvshow.rating)
        txt_year_rating.text = yearRating
        txt_director.text = getString(R.string.creator)
        txt_director_name.text = tvshow.creator
        txt_screenplay.visibility = View.INVISIBLE
        txt_cast.text = getString(R.string.cast_tvshow)
        Glide.with(this).load(tvshow.castPhoto1).into(img_cast_1)
        Glide.with(this).load(tvshow.castPhoto2).into(img_cast_2)
        Glide.with(this).load(tvshow.castPhoto3).into(img_cast_3)
        txt_cast_1.text = tvshow.cast1
        txt_cast_2.text = tvshow.cast2
        txt_cast_3.text = tvshow.cast3
        txt_detail_description.text = tvshow.description
        txt_language.text = tvshow.language
        txt_runtime.text = tvshow.runtime
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    sealed class DetailType : Serializable {
        object MOVIE : DetailType()
        object TVSHOW : DetailType()
    }

}
