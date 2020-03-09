package id.idham.moviecatalogue.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import id.idham.moviecatalogue.data.db.AppDatabase
import id.idham.moviecatalogue.data.db.dao.MovieDao
import id.idham.moviecatalogue.data.db.entity.Movie


class FavoriteProvider : ContentProvider() {

    companion object {

        private const val AUTHORITY = "id.idham.moviecatalogue.provider"
        private const val MOVIE = 1

        private lateinit var movieDao: MovieDao

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, Movie.TABLE_NAME, MOVIE)
        }
    }

    override fun onCreate(): Boolean {
        movieDao = AppDatabase.getInstance(context as Context).movieDao()
        return true
    }

    override fun query(
        uri: Uri, strings: Array<String>?, s: String?, strings1: Array<String>?, s1: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            MOVIE -> movieDao.getAllCursor()
            else -> null
        }
    }


    override fun getType(uri: Uri): String? {
        return null
    }


    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        return null
    }


    override fun update(
        uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        return 0
    }
}
