package id.idham.moviecatalogue.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import id.idham.moviecatalogue.BuildConfig

/**
 * Created by idhammi on 2/7/2020.
 */

data class TvShowResponse(
    @SerializedName("page") val page: String,
    @SerializedName("total_results") val totalResults: String,
    @SerializedName("total_pages") val totalPages: String,
    @SerializedName("results") val results: List<TvShowModel>
) {
    fun getListResult(): List<TvShowModel> {
        return when {
            results.isNullOrEmpty() -> listOf()
            else -> results
        }
    }
}

@Parcelize
data class TvShowModel(
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("name") val name: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("overview") val overview: String,
    @SerializedName("first_air_date") val firstAirDate: String
) : Parcelable {
    fun getYearReleased(): String {
        return firstAirDate.take(4)
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