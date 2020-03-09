package id.idham.moviefavorite.data

import android.net.Uri
import id.idham.moviefavorite.data.entity.Movie

/**
 * Created by idhammi on 3/3/2020.
 */


object DataManager {

    private const val AUTHORITY = "id.idham.moviecatalogue.provider"
    private const val SCHEME = "content"

    val URI_MOVIE: Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(Movie.TABLE_NAME)
        .build()

}