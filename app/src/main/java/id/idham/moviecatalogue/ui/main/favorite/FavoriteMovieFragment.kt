package id.idham.moviecatalogue.ui.main.favorite


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.common.DiffCallback
import id.idham.moviecatalogue.common.RecyclerAdapter
import id.idham.moviecatalogue.data.db.entity.Movie
import id.idham.moviecatalogue.data.network.response.MovieModel
import id.idham.moviecatalogue.extension.goneIf
import id.idham.moviecatalogue.extension.visible
import id.idham.moviecatalogue.ui.base.BaseFragment
import id.idham.moviecatalogue.ui.detail.DetailActivity
import id.idham.moviecatalogue.ui.detail.DetailActivity.DetailType
import kotlinx.android.synthetic.main.fragment_favorite_movie.view.*
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.layout_no_result.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteMovieFragment : BaseFragment() {

    companion object {
        private const val REQUEST_CODE = 100
    }

    private val viewModel by viewModel<FavoriteMovieViewModel>()
    private val diffCallback by inject<DiffCallback>()

    private val movieAdapter by lazy {
        RecyclerAdapter<Movie>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_movie,
            onBind = { item, view ->
                setupMovieDisplay(item, view)
            },
            itemListener = { item, _, _ ->
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.IntentKey.ITEM, convertToMovieModel(item))
                intent.putExtra(DetailActivity.IntentKey.TYPE, DetailType.MOVIE)
                startActivityForResult(intent, REQUEST_CODE)
            }
        )
    }

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite_movie, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(rootView.rv_movie_favorite) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
        }
        observingData()
        setupSwipeRefresh()
    }

    private fun observingData() {
        with(viewModel) {
            observeData().onResult {
                movieAdapter.setData(it)
                rootView.rv_movie_favorite.visible()
            }
            observeLoading().onResult {
                setVisibilityProgress(it)
            }
            observeEmptyData().onResult {
                setVisibilityEmptyData(it)
            }
        }
        viewModel.getData()
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

    private fun setVisibilityProgress(isVisible: Boolean) {
        rootView.swipe_movie_favorite?.isRefreshing = isVisible
    }

    private fun setVisibilityEmptyData(isEmpty: Boolean) {
        rootView.swipe_movie_favorite?.isRefreshing = !isEmpty
        rootView.layout_no_result.goneIf(!isEmpty)
    }

    private fun setupSwipeRefresh() {
        with(rootView.swipe_movie_favorite) {
            isEnabled = true
            setOnRefreshListener {
                viewModel.getData()
            }
            setColorSchemeColors(
                androidx.core.content.ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimary
                ),
                androidx.core.content.ContextCompat.getColor(requireContext(), R.color.colorAccent),
                androidx.core.content.ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimaryDark
                )
            )
            val typedValue = android.util.TypedValue()
            context.theme?.resolveAttribute(
                androidx.appcompat.R.attr.actionBarSize,
                typedValue,
                true
            )
            setProgressViewOffset(
                false,
                0,
                context.resources.getDimensionPixelSize(typedValue.resourceId)
            )
            isRefreshing = true
        }
    }

    private fun convertToMovieModel(movie: Movie): MovieModel {
        return MovieModel(
            movie.movieId,
            movie.posterPath,
            movie.originalLanguage,
            movie.title,
            movie.voteAverage,
            movie.overview,
            movie.releaseDate
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            viewModel.getData()
        }
    }

}
