package id.idham.moviecatalogue.model

import com.google.gson.annotations.SerializedName

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