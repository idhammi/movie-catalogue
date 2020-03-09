package id.idham.moviefavorite.ui.main

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.idham.moviefavorite.R
import id.idham.moviefavorite.common.DiffCallback
import id.idham.moviefavorite.common.RecyclerAdapter
import id.idham.moviefavorite.data.DataManager
import id.idham.moviefavorite.data.entity.Movie
import id.idham.moviefavorite.ui.base.BaseActivity
import id.idham.moviefavorite.ui.detail.DetailActivity
import id.idham.moviefavorite.ui.extension.goneIf
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.layout_no_result.*

class MainActivity : BaseActivity() {

    companion object {
        private const val LOADER_MOVIE = 1
        private const val REQUEST_CODE = 100
    }

    private val movieAdapter by lazy {
        RecyclerAdapter<Movie>(
            diffCallback = DiffCallback(),
            holderResId = R.layout.item_movie,
            onBind = { item, view ->
                setupMovieDisplay(item, view)
            },
            itemListener = { item, _, _ ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(DetailActivity.IntentKey.ITEM, item)
                startActivityForResult(intent, REQUEST_CODE)
            }
        )
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        rv_movie_favorite.adapter = movieAdapter
        rv_movie_favorite.layoutManager = LinearLayoutManager(this)
        LoaderManager.getInstance(this).initLoader(LOADER_MOVIE, null, mLoaderCallbacks)
    }

    private fun setupMovieDisplay(item: Movie, view: View) {
        with(view) {
            Glide.with(context).load(item.getPosterFullPath())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions().placeholder(R.color.colorGrey))
                .apply(RequestOptions().error(R.color.colorGrey))
                .into(img_photo)
            txt_name.text = item.title
            txt_description.text = item.overview
        }
    }

    private val mLoaderCallbacks = object : LoaderManager.LoaderCallbacks<Cursor> {

        @NonNull
        override fun onCreateLoader(id: Int, @Nullable args: Bundle?): Loader<Cursor> {
            return CursorLoader(
                this@MainActivity,
                DataManager.URI_MOVIE,
                arrayOf(Movie.COLUMN_TITLE), null, null, null
            )
        }

        override fun onLoadFinished(@NonNull loader: Loader<Cursor>, data: Cursor) {
            movieAdapter.addData(mapCursorToArrayList(data))
            layout_no_result.goneIf(movieAdapter.itemCount != 0)
        }

        override fun onLoaderReset(@NonNull loader: Loader<Cursor>) {
            movieAdapter.clearData()
        }

    }

    fun mapCursorToArrayList(movieCursor: Cursor?): ArrayList<Movie> {
        val notesList = ArrayList<Movie>()

        movieCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(Movie.COLUMN_ID))
                val movieId = getInt(getColumnIndexOrThrow(Movie.COLUMN_MOVIE_ID))
                val posterPath = getString(getColumnIndexOrThrow(Movie.COLUMN_POSTER))
                val originalLanguage = getString(getColumnIndexOrThrow(Movie.COLUMN_LANGUAGE))
                val title = getString(getColumnIndexOrThrow(Movie.COLUMN_TITLE))
                val voteAverage = getDouble(getColumnIndexOrThrow(Movie.COLUMN_VOTE))
                val overview = getString(getColumnIndexOrThrow(Movie.COLUMN_OVERVIEW))
                val releaseDate = getString(getColumnIndexOrThrow(Movie.COLUMN_RELEASE_DATE))
                notesList.add(
                    Movie(
                        id,
                        movieId,
                        posterPath,
                        originalLanguage,
                        title,
                        voteAverage,
                        overview,
                        releaseDate
                    )
                )
            }
        }
        return notesList
    }


}
