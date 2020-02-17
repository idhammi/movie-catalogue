package id.idham.moviecatalogue.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import id.idham.moviecatalogue.BuildConfig

/**
 * Created by idhammi on 2/7/2020.
 */

data class MovieResponse(
    @SerializedName("page") val page: String,
    @SerializedName("total_results") val totalResults: String,
    @SerializedName("total_pages") val totalPages: String,
    @SerializedName("results") val results: List<MovieModel>
) {
    fun getListResult(): List<MovieModel> {
        return when {
            results.isNullOrEmpty() -> listOf()
            else -> results
        }
    }
}

@Parcelize
data class MovieModel(
    @SerializedName("id") val id: Int,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String
) : Parcelable {
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