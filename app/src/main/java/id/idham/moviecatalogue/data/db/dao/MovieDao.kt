package id.idham.moviecatalogue.data.db.dao

import android.database.Cursor
import androidx.room.*
import id.idham.moviecatalogue.data.db.entity.Movie
import io.reactivex.Single

/**
 * Created by idhammi on 2/16/2020.
 */

@Dao
interface MovieDao {

    @Query("SELECT * FROM ${Movie.TABLE_NAME}")
    fun getAll(): Single<List<Movie>>

    @Query("SELECT * FROM ${Movie.TABLE_NAME}")
    fun getAllList(): List<Movie>

    @Query("SELECT * FROM ${Movie.TABLE_NAME}")
    fun getAllCursor(): Cursor

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE movie_id IN (:movieId)")
    fun getById(movieId: Int): Single<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM ${Movie.TABLE_NAME} WHERE movie_id IN (:movieId)")
    fun deleteById(movieId: Int)

}