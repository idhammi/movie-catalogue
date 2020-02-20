package id.idham.moviecatalogue.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.idham.moviecatalogue.data.db.AppDatabase.Companion.DB_VERSION
import id.idham.moviecatalogue.data.db.dao.MovieDao
import id.idham.moviecatalogue.data.db.dao.TvShowDao
import id.idham.moviecatalogue.data.db.entity.Movie
import id.idham.moviecatalogue.data.db.entity.TvShow

/**
 * Created by idhammi on 2/16/2020.
 */

@Database(entities = [Movie::class, TvShow::class], version = DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "movie.db"
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                .build()

    }
}