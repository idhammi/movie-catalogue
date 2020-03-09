package id.idham.moviecatalogue.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.idham.moviecatalogue.BuildConfig
import id.idham.moviecatalogue.data.network.response.MovieModel

/**
 * Created by idhammi on 12/27/2019.
 */

@Entity(tableName = Movie.TABLE_NAME)
data class Movie(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(index = true, name = COLUMN_ID) val id: Int = 0,
    @ColumnInfo(name = COLUMN_MOVIE_ID) val movieId: Int,
    @ColumnInfo(name = COLUMN_POSTER) val posterPath: String,
    @ColumnInfo(name = COLUMN_LANGUAGE) val originalLanguage: String,
    @ColumnInfo(name = COLUMN_TITLE) val title: String,
    @ColumnInfo(name = COLUMN_VOTE) val voteAverage: Double,
    @ColumnInfo(name = COLUMN_OVERVIEW) val overview: String,
    @ColumnInfo(name = COLUMN_RELEASE_DATE) val releaseDate: String
) {
    companion object {
        const val TABLE_NAME = "movie"
        const val COLUMN_ID  = "_id"
        const val COLUMN_MOVIE_ID  = "movie_id"
        const val COLUMN_POSTER  = "poster_path"
        const val COLUMN_LANGUAGE  = "original_language"
        const val COLUMN_TITLE  = "title"
        const val COLUMN_VOTE  = "vote_average"
        const val COLUMN_OVERVIEW  = "overview"
        const val COLUMN_RELEASE_DATE  = "release_date"

        fun to(movieModel: MovieModel): Movie {
            return Movie(
                movieId = movieModel.id,
                posterPath = movieModel.posterPath,
                originalLanguage = movieModel.originalLanguage,
                title = movieModel.title,
                voteAverage = movieModel.voteAverage,
                overview = movieModel.overview,
                releaseDate = movieModel.releaseDate
            )
        }
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