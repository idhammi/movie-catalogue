package id.idham.moviecatalogue.util

import androidx.lifecycle.LiveData
import id.idham.moviecatalogue.common.LiveEvent

/**
 * Created by idhammi on 2/28/2020.
 */

fun <T> LiveData<T>.toSingleEvent(): LiveData<T> {
    val result = LiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}