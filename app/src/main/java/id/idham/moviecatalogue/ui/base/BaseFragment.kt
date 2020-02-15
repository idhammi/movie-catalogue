package id.idham.moviecatalogue.ui.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import id.idham.moviecatalogue.common.ConnectionLiveData
import org.koin.android.ext.android.inject

/**
 * Created by idhammi on 2/7/2020.
 */

abstract class BaseFragment : Fragment() {

    private val connectionLiveData by inject<ConnectionLiveData>()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseFragment, Observer { data -> data?.let(action) })
    }

    protected fun boundNetwork(action: (Boolean) -> Unit = {}) {
        connectionLiveData.onResult {
            action.invoke(it)
        }
    }

}