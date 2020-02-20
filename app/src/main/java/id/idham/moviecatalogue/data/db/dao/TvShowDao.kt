package id.idham.moviecatalogue.data.db.dao

import androidx.room.*
import id.idham.moviecatalogue.data.db.entity.TvShow
import io.reactivex.Single

/**
 * Created by idhammi on 2/16/2020.
 */

@Dao
interface TvShowDao {

    @Query("SELECT * FROM TvShow")
    fun getAll(): Single<List<TvShow>>

    @Query("SELECT * FROM TvShow WHERE tv_show_id IN (:tvShowId)")
    fun getById(tvShowId: Int): Single<List<TvShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tvShow: TvShow)

    @Delete
    fun delete(tvShow: TvShow)

    @Query("DELETE FROM TvShow WHERE tv_show_id IN (:tvShowId)")
    fun deleteById(tvShowId: Int)

}