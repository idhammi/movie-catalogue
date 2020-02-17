package id.idham.moviecatalogue.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.idham.moviecatalogue.BuildConfig

/**
 * Created by idhammi on 12/27/2019.
 */

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "release_date") val releaseDate: String
) {
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