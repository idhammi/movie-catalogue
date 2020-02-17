package id.idham.moviecatalogue.ui.main.tvshow


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
import id.idham.moviecatalogue.extension.gone
import id.idham.moviecatalogue.extension.goneIf
import id.idham.moviecatalogue.extension.isVisible
import id.idham.moviecatalogue.extension.visible
import id.idham.moviecatalogue.data.network.response.TvShowModel
import id.idham.moviecatalogue.data.network.NetworkError
import id.idham.moviecatalogue.ui.base.BaseFragment
import id.idham.moviecatalogue.ui.detail.DetailActivity
import id.idham.moviecatalogue.ui.detail.DetailActivity.DetailType
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.android.synthetic.main.fragment_tv_show.view.*
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.layout_no_internet_error.view.*
import kotlinx.android.synthetic.main.layout_no_result.view.*
import kotlinx.android.synthetic.main.layout_server_error.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : BaseFragment() {

    private val viewModel by viewModel<TvShowViewModel>()
    private val diffCallback by inject<DiffCallback>()

    private var isConnected: Boolean = true

    private val tvShowAdapter by lazy {
        RecyclerAdapter<TvShowModel>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_movie,
            onBind = { item, view ->
                setupTvShowDisplay(item, view)
            },
            itemListener = { item, _, _ ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.IntentKey.ITEM, item)
                intent.putExtra(DetailActivity.IntentKey.TYPE, DetailType.TVSHOW)
                startActivity(intent)
            }
        )
    }

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tv_show, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(rootView.rv_tv_show) {
            adapter = tvShowAdapter
            layoutManager = LinearLayoutManager(context)
        }
        setupInternetRetry { fetchData() }
        setupServerErrorRetry { fetchData() }
        observingData()
        setupSwipeRefresh()
    }

    private fun observingData() {
        boundNetwork {
            setVisibilityNoInternet(!it)
            isConnected = it
        }
        with(viewModel) {
            observeData().onResult {
                tvShowAdapter.setData(it)
                rv_tv_show.visible()
            }
            observeLoading().onResult {
                setVisibilityProgress(it)
            }
            observeEmptyData().onResult {
                setVisibilityEmptyData(it)
            }
            observeError().onResult {
                handleErrorMapping(it)
            }
        }
        fetchData()
    }

    private fun fetchData() {
        setVisibilityNoInternet(!isConnected)
        if (isConnected) viewModel.getData()
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

    private fun setVisibilityProgress(isVisible: Boolean) {
        rootView.swipe_tv_show?.isRefreshing = isVisible
    }

    private fun setVisibilityEmptyData(isEmpty: Boolean) {
        rootView.swipe_tv_show?.isRefreshing = !isEmpty
        rootView.layout_no_result.goneIf(!isEmpty)
    }

    private fun setVisibilityNoInternet(isError: Boolean) {
        if (isError && rootView.layout_server_error.isVisible()) {
            rootView.layout_server_error.gone()
        }
        with(rootView) {
            swipe_tv_show?.goneIf(isError)
            layout_no_internet?.goneIf(!isError)
        }
    }

    private fun setVisibilityServerError(isError: Boolean) {
        val allowToShow = isError && !rootView.layout_no_internet.isVisible()
        with(rootView) {
            swipe_tv_show?.goneIf(allowToShow)
            layout_server_error?.goneIf(!allowToShow)
        }
    }

    private fun setupSwipeRefresh() {
        with(rootView.swipe_tv_show) {
            isEnabled = true
            setOnRefreshListener {
                fetchData()
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

    private fun setupInternetRetry(action: () -> Unit) {
        rootView.layout_no_internet.tv_no_internet_retry.setOnClickListener {
            rootView.layout_no_internet.gone()
            action.invoke()
        }
    }

    private fun setupServerErrorRetry(action: () -> Unit) {
        rootView.layout_server_error.tv_server_error_retry.setOnClickListener {
            rootView.layout_server_error.gone()
            action.invoke()
        }
    }

    private fun handleErrorMapping(err: NetworkError?) {
        if (null != err) setVisibilityServerError(true)
        else setVisibilityServerError(false)
    }

}
