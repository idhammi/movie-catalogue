package id.idham.moviefavorite.data.entity

import android.os.Parcelable
import id.idham.moviefavorite.BuildConfig
import kotlinx.android.parcel.Parcelize

/**
 * Created by idhammi on 12/27/2019.
 */

@Parcelize
data class Movie(
    val id: Int = 0,
    val movieId: Int,
    val posterPath: String,
    val originalLanguage: String,
    val title: String,
    val voteAverage: Double,
    val overview: String,
    val releaseDate: String
) : Parcelable {
    companion object {
        const val TABLE_NAME = "movie"
        const val COLUMN_ID = "_id"
        const val COLUMN_MOVIE_ID = "movie_id"
        const val COLUMN_POSTER = "poster_path"
        const val COLUMN_LANGUAGE = "original_language"
        const val COLUMN_TITLE = "title"
        const val COLUMN_VOTE = "vote_average"
        const val COLUMN_OVERVIEW = "overview"
        const val COLUMN_RELEASE_DATE = "release_date"
    }

    fun getYearReleased(): String {
        return releaseDate.take(4)
    }

    fun getPosterFullPath(original: Boolean = false, size: Int = 185): String {
        /**
         * poster size:
         * w92, w154, w185, w342, w500, w780, or original
         */
        return if (original) "${BuildConfig.IMG_URL}/original/$posterPath"
        else "${BuildConfig.IMG_URL}/w$size/$posterPath"
    }
}