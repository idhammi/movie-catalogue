package id.idham.moviecatalogue.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import id.idham.moviecatalogue.common.ConnectionLiveData
import org.koin.android.ext.android.inject

/**
 * Created by idhammi on 2/7/2020.
 */

abstract class BaseActivity : AppCompatActivity() {

    private val connectionLiveData by inject<ConnectionLiveData>()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseActivity, Observer { data -> data?.let(action) })
    }

    protected fun boundNetwork(action: (Boolean) -> Unit = {}) {
        connectionLiveData.onResult {
            action.invoke(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetupLayout(savedInstanceState)
        onViewReady(savedInstanceState)
    }

    protected abstract fun onSetupLayout(savedInstanceState: Bundle?)

    protected abstract fun onViewReady(savedInstanceState: Bundle?)

}