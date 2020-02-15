package id.idham.moviecatalogue.ui.main.favorite


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = FavoriteAdapter(activity as Activity, childFragmentManager)
        view_pager.adapter = adapter
        tabs.setupWithViewPager(view_pager)
    }


}
