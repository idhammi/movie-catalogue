package id.idham.moviecatalogue.data.db.dao

import androidx.room.*
import id.idham.moviecatalogue.data.db.entity.Movie
import io.reactivex.Single

/**
 * Created by idhammi on 2/16/2020.
 */

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): Single<List<Movie>>

    @Query("SELECT * FROM Movie WHERE movie_id IN (:movieId)")
    fun getById(movieId: Int): Single<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM Movie WHERE movie_id IN (:movieId)")
    fun deleteById(movieId: Int)

}