package id.idham.moviecatalogue.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.model.MovieModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.img_photo
import kotlinx.android.synthetic.main.item_movie.txt_name

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as MovieModel

        Glide.with(this).load(movie.photo).into(img_photo)
        txt_name.text = movie.name
        val yearRating = "${movie.year} | Rating : ${movie.rating}"
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
