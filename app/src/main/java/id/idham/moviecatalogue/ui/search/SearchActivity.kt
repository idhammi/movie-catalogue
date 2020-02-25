package id.idham.moviecatalogue.ui.search

import android.os.Bundle
import android.view.MenuItem
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.extension.toast
import id.idham.moviecatalogue.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*
import java.io.Serializable

class SearchActivity : BaseActivity() {

    object IntentKey {
        const val TYPE = "DetailActivity.TYPE"
    }

    sealed class Type : Serializable {
        object MOVIE : Type()
        object TVSHOW : Type()
    }

    private val type: Type
        get() = intent.getSerializableExtra(IntentKey.TYPE) as Type

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_search)
        setSupportActionBar(tb_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        if (type is Type.MOVIE) toast("Movie")
        else toast("TV Show")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}
