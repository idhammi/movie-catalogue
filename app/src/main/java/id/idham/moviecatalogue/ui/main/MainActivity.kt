package id.idham.moviecatalogue.ui.main

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.model.MovieModel
import id.idham.moviecatalogue.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var moviePhoto: TypedArray
    private lateinit var movieName: Array<String>
    private lateinit var movieDescription: Array<String>
    private lateinit var movieYear: Array<String>
    private lateinit var movieRating: Array<String>
    private lateinit var movieDirector: Array<String>
    private lateinit var movieScreenplay: Array<String>
    private lateinit var movieCast1: Array<String>
    private lateinit var movieCast2: Array<String>
    private lateinit var movieCast3: Array<String>
    private lateinit var movieCastPhoto1: TypedArray
    private lateinit var movieCastPhoto2: TypedArray
    private lateinit var movieCastPhoto3: TypedArray
    private lateinit var movieLanguage: Array<String>
    private lateinit var movieRuntime: Array<String>
    private var movies = arrayListOf<MovieModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(this)
        lv_movie.adapter = adapter

        prepare()
        addItem()

        lv_movie.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, movies[position])
            startActivity(intent)
        }
    }

    private fun prepare() {
        moviePhoto = resources.obtainTypedArray(R.array.movie_photo)
        movieName = resources.getStringArray(R.array.movie_name)
        movieDescription = resources.getStringArray(R.array.movie_description)
        movieYear = resources.getStringArray(R.array.movie_year)
        movieRating = resources.getStringArray(R.array.movie_rating)
        movieDirector = resources.getStringArray(R.array.movie_director)
        movieScreenplay = resources.getStringArray(R.array.movie_screenplay)
        movieCast1 = resources.getStringArray(R.array.movie_cast_1)
        movieCast2 = resources.getStringArray(R.array.movie_cast_2)
        movieCast3 = resources.getStringArray(R.array.movie_cast_3)
        movieCastPhoto1 = resources.obtainTypedArray(R.array.movie_cast_photo_1)
        movieCastPhoto2 = resources.obtainTypedArray(R.array.movie_cast_photo_2)
        movieCastPhoto3 = resources.obtainTypedArray(R.array.movie_cast_photo_3)
        movieLanguage = resources.getStringArray(R.array.movie_language)
        movieRuntime = resources.getStringArray(R.array.movie_runtime)
    }

    private fun addItem() {
        for (position in movieName.indices) {
            val movie = MovieModel(
                moviePhoto.getResourceId(position, -1),
                movieName[position],
                movieDescription[position],
                movieYear[position],
                movieRating[position],
                movieDirector[position],
                movieScreenplay[position],
                movieCast1[position],
                movieCast2[position],
                movieCast3[position],
                movieCastPhoto1.getResourceId(position, -1),
                movieCastPhoto2.getResourceId(position, -1),
                movieCastPhoto3.getResourceId(position, -1),
                movieLanguage[position],
                movieRuntime[position]
            )
            movies.add(movie)
        }
        adapter.movies = movies
    }
}
