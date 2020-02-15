package id.idham.moviecatalogue.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.idham.moviecatalogue.BuildConfig
import kotlinx.android.parcel.Parcelize

/**
 * Created by idhammi on 1/25/2020.
 */

@Parcelize
data class TvShowModel(
    @SerializedName("id") val id: Int,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("origin_country") val originCountry: List<String>,
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