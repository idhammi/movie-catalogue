package id.idham.moviefavorite.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by idhammi on 2/7/2020.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetupLayout(savedInstanceState)
        onViewReady(savedInstanceState)
    }

    protected abstract fun onSetupLayout(savedInstanceState: Bundle?)

    protected abstract fun onViewReady(savedInstanceState: Bundle?)

}