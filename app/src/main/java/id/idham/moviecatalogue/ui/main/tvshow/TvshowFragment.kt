package id.idham.moviecatalogue.ui.main.tvshow


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
import id.idham.moviecatalogue.model.TvshowModel
import id.idham.moviecatalogue.ui.detail.DetailActivity
import id.idham.moviecatalogue.ui.detail.DetailActivity.DetailType
import kotlinx.android.synthetic.main.fragment_tvshow.*
import kotlinx.android.synthetic.main.item_movie.view.*

class TvshowFragment : Fragment() {

    private lateinit var diffCallback: DiffCallback

    private val tvshowAdapter by lazy {
        RecyclerAdapter<TvshowModel>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_movie,
            onBind = { item, view ->
                setupTvshowDisplay(item, view)
            },
            itemListener = { item, _, _ ->
                val intent =
                    Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.IntentKey.ITEM, item)
                intent.putExtra(DetailActivity.IntentKey.TYPE, DetailType.TVSHOW)
                startActivity(intent)
            }
        )
    }

    private lateinit var tvshowPhoto: Array<String>
    private lateinit var tvshowName: Array<String>
    private lateinit var tvshowDescription: Array<String>
    private lateinit var tvshowYear: Array<String>
    private lateinit var tvshowRating: Array<String>
    private lateinit var tvshowCreator: Array<String>
    private lateinit var tvshowCast1: Array<String>
    private lateinit var tvshowCast2: Array<String>
    private lateinit var tvshowCast3: Array<String>
    private lateinit var tvshowCastPhoto1: Array<String>
    private lateinit var tvshowCastPhoto2: Array<String>
    private lateinit var tvshowCastPhoto3: Array<String>
    private lateinit var tvshowLanguage: Array<String>
    private lateinit var tvshowRuntime: Array<String>
    private var tvshows = arrayListOf<TvshowModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diffCallback = DiffCallback()

        rv_tvshow.layoutManager = LinearLayoutManager(context)
        rv_tvshow.adapter = tvshowAdapter

        tvshowAdapter.setData(getListTvshow())
    }

    private fun getListTvshow(): ArrayList<TvshowModel> {
        tvshowPhoto = resources.getStringArray(R.array.tv_photo)
        tvshowName = resources.getStringArray(R.array.tv_name)
        tvshowDescription = resources.getStringArray(R.array.tv_description)
        tvshowYear = resources.getStringArray(R.array.tv_year)
        tvshowRating = resources.getStringArray(R.array.tv_rating)
        tvshowCreator = resources.getStringArray(R.array.tv_creator)
        tvshowCast1 = resources.getStringArray(R.array.tv_cast_1)
        tvshowCast2 = resources.getStringArray(R.array.tv_cast_2)
        tvshowCast3 = resources.getStringArray(R.array.tv_cast_3)
        tvshowCastPhoto1 = resources.getStringArray(R.array.tv_cast_photo_1)
        tvshowCastPhoto2 = resources.getStringArray(R.array.tv_cast_photo_2)
        tvshowCastPhoto3 = resources.getStringArray(R.array.tv_cast_photo_3)
        tvshowLanguage = resources.getStringArray(R.array.tv_language)
        tvshowRuntime = resources.getStringArray(R.array.tv_runtime)

        for (position in tvshowName.indices) {
            val movie = TvshowModel(
                tvshowPhoto[position],
                tvshowName[position],
                tvshowDescription[position],
                tvshowYear[position],
                tvshowRating[position],
                tvshowCreator[position],
                tvshowCast1[position],
                tvshowCast2[position],
                tvshowCast3[position],
                tvshowCastPhoto1[position],
                tvshowCastPhoto2[position],
                tvshowCastPhoto3[position],
                tvshowLanguage[position],
                tvshowRuntime[position]
            )
            tvshows.add(movie)
        }
        return tvshows
    }

    private fun setupTvshowDisplay(item: TvshowModel, view: View) {
        with(view) {
            Glide.with(context)
                .load(item.photo)
                .into(img_photo)
            txt_name.text = item.name
            txt_description.text = item.description
        }
    }

}
