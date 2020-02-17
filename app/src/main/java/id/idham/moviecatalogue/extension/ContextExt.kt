package id.idham.moviecatalogue.extension

import android.content.Context
import android.widget.Toast

/**
 * Created by idhammi on 2/17/2020.
 */

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()