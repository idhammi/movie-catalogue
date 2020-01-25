package id.idham.moviecatalogue.ui.main.movie


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.common.DiffCallback
import id.idham.moviecatalogue.common.RecyclerAdapter
import id.idham.moviecatalogue.model.MovieModel
import id.idham.moviecatalogue.ui.detail.DetailActivity
import id.idham.moviecatalogue.ui.detail.DetailActivity.DetailType
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieFragment : Fragment() {

    private lateinit var diffCallback: DiffCallback

    private val movieAdapter by lazy {
        RecyclerAdapter<MovieModel>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_movie,
            onBind = { item, view ->
                setupMovieDisplay(item, view)
            },
            itemListener = { item, _, _ ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.IntentKey.ITEM, item)
                intent.putExtra(DetailActivity.IntentKey.TYPE, DetailType.MOVIE)
                startActivity(intent)
            }
        )
    }

    private lateinit var moviePhoto: Array<String>
    private lateinit var movieName: Array<String>
    private lateinit var movieDescription: Array<String>
    private lateinit var movieYear: Array<String>
    private lateinit var movieRating: Array<String>
    private lateinit var movieDirector: Array<String>
    private lateinit var movieScreenplay: Array<String>
    private lateinit var movieCast1: Array<String>
    private lateinit var movieCast2: Array<String>
    private lateinit var movieCast3: Array<String>
    private lateinit var movieCastPhoto1: Array<String>
    private lateinit var movieCastPhoto2: Array<String>
    private lateinit var movieCastPhoto3: Array<String>
    private lateinit var movieLanguage: Array<String>
    private lateinit var movieRuntime: Array<String>
    private var movies = arrayListOf<MovieModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diffCallback = DiffCallback()

        rv_movie.layoutManager = LinearLayoutManager(context)
        rv_movie.adapter = movieAdapter

        movieAdapter.setData(getListMovie())
    }

    private fun getListMovie(): ArrayList<MovieModel> {
        moviePhoto = resources.getStringArray(R.array.movie_photo)
        movieName = resources.getStringArray(R.array.movie_name)
        movieDescription = resources.getStringArray(R.array.movie_description)
        movieYear = resources.getStringArray(R.array.movie_year)
        movieRating = resources.getStringArray(R.array.movie_rating)
        movieDirector = resources.getStringArray(R.array.movie_director)
        movieScreenplay = resources.getStringArray(R.array.movie_screenplay)
        movieCast1 = resources.getStringArray(R.array.movie_cast_1)
        movieCast2 = resources.getStringArray(R.array.movie_cast_2)
        movieCast3 = resources.getStringArray(R.array.movie_cast_3)
        movieCastPhoto1 = resources.getStringArray(R.array.movie_cast_photo_1)
        movieCastPhoto2 = resources.getStringArray(R.array.movie_cast_photo_2)
        movieCastPhoto3 = resources.getStringArray(R.array.movie_cast_photo_3)
        movieLanguage = resources.getStringArray(R.array.movie_language)
        movieRuntime = resources.getStringArray(R.array.movie_runtime)

        for (position in movieName.indices) {
            val movie = MovieModel(
                moviePhoto[position],
                movieName[position],
                movieDescription[position],
                movieYear[position],
                movieRating[position],
                movieDirector[position],
                movieScreenplay[position],
                movieCast1[position],
                movieCast2[position],
                movieCast3[position],
                movieCastPhoto1[position],
                movieCastPhoto2[position],
                movieCastPhoto3[position],
                movieLanguage[position],
                movieRuntime[position]
            )
            movies.add(movie)
        }
        return movies
    }

    private fun setupMovieDisplay(item: MovieModel, view: View) {
        with(view) {
            Glide.with(context)
                .load(item.photo)
                .into(img_photo)
            txt_name.text = item.name
            txt_description.text = item.description
        }
    }


}
