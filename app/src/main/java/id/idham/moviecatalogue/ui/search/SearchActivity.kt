package id.idham.moviecatalogue.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.common.DiffCallback
import id.idham.moviecatalogue.common.RecyclerAdapter
import id.idham.moviecatalogue.data.network.NetworkError
import id.idham.moviecatalogue.data.network.response.MovieModel
import id.idham.moviecatalogue.data.network.response.TvShowModel
import id.idham.moviecatalogue.extension.gone
import id.idham.moviecatalogue.extension.goneIf
import id.idham.moviecatalogue.extension.isVisible
import id.idham.moviecatalogue.extension.visible
import id.idham.moviecatalogue.ui.base.BaseActivity
import id.idham.moviecatalogue.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.layout_no_internet_error.*
import kotlinx.android.synthetic.main.layout_no_internet_error.view.*
import kotlinx.android.synthetic.main.layout_no_result.*
import kotlinx.android.synthetic.main.layout_server_error.*
import kotlinx.android.synthetic.main.layout_server_error.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.Serializable

class SearchActivity : BaseActivity(), SearchView.OnQueryTextListener {

    object IntentKey {
        const val TYPE = "DetailActivity.TYPE"
    }

    sealed class Type : Serializable {
        object MOVIE : Type()
        object TVSHOW : Type()
    }

    private val type: Type
        get() = intent.getSerializableExtra(IntentKey.TYPE) as Type

    private val viewModel by viewModel<SearchViewModel>()
    private val diffCallback by inject<DiffCallback>()

    private var isConnected: Boolean = true
    private var isResumed: Boolean = false

    private lateinit var query: String

    private val movieAdapter by lazy {
        RecyclerAdapter<MovieModel>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_movie,
            onBind = { item, view ->
                setupMovieDisplay(item, view)
            },
            itemListener = { item, _, _ ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(DetailActivity.IntentKey.ITEM, item)
                intent.putExtra(DetailActivity.IntentKey.TYPE, DetailActivity.DetailType.MOVIE)
                startActivity(intent)
                isResumed = true
            }
        )
    }

    private val tvShowAdapter by lazy {
        RecyclerAdapter<TvShowModel>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_movie,
            onBind = { item, view ->
                setupTvShowDisplay(item, view)
            },
            itemListener = { item, _, _ ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(DetailActivity.IntentKey.ITEM, item)
                intent.putExtra(DetailActivity.IntentKey.TYPE, DetailActivity.DetailType.TVSHOW)
                startActivity(intent)
                isResumed = true
            }
        )
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_search)
        setSupportActionBar(tb_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        boundNetwork {
            setVisibilityNoInternet(!it)
            isConnected = it
        }

        rv_search.layoutManager = LinearLayoutManager(this)
        if (type is Type.MOVIE) setupMovie()
        else setupTvShow()

        with(viewModel) {
            observeEmptyData().onResult {
                pb_search.gone()
                setVisibilityEmptyData(it)
            }
            observeError().onResult {
                pb_search.gone()
                handleErrorMapping(it)
            }
        }

        searchView.setOnQueryTextListener(this)
        searchView.requestFocus()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        if (isResumed) searchView.clearFocus()
        super.onResume()
    }

    private fun setupMovie() {
        rv_search.adapter = movieAdapter
        setupInternetRetry { fetchMovie(query) }
        setupServerErrorRetry { fetchMovie(query) }
        with(viewModel) {
            observeMovie().onResult {
                pb_search.gone()
                movieAdapter.setData(it)
                rv_search.visible()
            }
        }
    }

    private fun setupTvShow() {
        rv_search.adapter = tvShowAdapter
        setupInternetRetry { fetchTvShow(query) }
        setupServerErrorRetry { fetchTvShow(query) }
        with(viewModel) {
            observeTvShow().onResult {
                pb_search.gone()
                tvShowAdapter.setData(it)
                rv_search.visible()
            }
        }
    }

    private fun fetchMovie(query: String) {
        setVisibilityNoInternet(!isConnected)
        if (isConnected) viewModel.getMovie(query)
    }

    private fun fetchTvShow(query: String) {
        setVisibilityNoInternet(!isConnected)
        if (isConnected) viewModel.getTvShow(query)
    }

    private fun setupMovieDisplay(item: MovieModel, view: View) {
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

    private fun setupTvShowDisplay(item: TvShowModel, view: View) {
        with(view) {
            Glide.with(context).load(item.getPosterFullPath())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions().placeholder(R.color.colorGrey))
                .apply(RequestOptions().error(R.color.colorGrey))
                .into(img_photo)
            txt_name.text = item.name
            txt_description.text = item.overview
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        this.query = query as String
        if (type is Type.MOVIE) fetchMovie(query)
        else fetchTvShow(query)
        pb_search.visible()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun setVisibilityEmptyData(isEmpty: Boolean) {
        layout_no_result.goneIf(!isEmpty)
    }

    private fun setVisibilityNoInternet(isError: Boolean) {
        if (isError && layout_server_error.isVisible()) {
            layout_server_error.gone()
        }
        layout_no_internet?.goneIf(!isError)
    }

    private fun setVisibilityServerError(isError: Boolean) {
        val allowToShow = isError && !layout_no_internet.isVisible()
        layout_server_error?.goneIf(!allowToShow)
    }

    private fun setupInternetRetry(action: () -> Unit) {
        layout_no_internet.tv_no_internet_retry.setOnClickListener {
            layout_no_internet.gone()
            action.invoke()
        }
    }

    private fun setupServerErrorRetry(action: () -> Unit) {
        layout_server_error.tv_server_error_retry.setOnClickListener {
            layout_server_error.gone()
            action.invoke()
        }
    }

    private fun handleErrorMapping(err: NetworkError?) {
        if (null != err) setVisibilityServerError(true)
        else setVisibilityServerError(false)
    }

}
