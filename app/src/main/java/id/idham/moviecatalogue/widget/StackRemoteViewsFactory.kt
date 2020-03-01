package id.idham.moviecatalogue.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.data.DataManager
import id.idham.moviecatalogue.data.db.entity.Movie

/**
 * Created by idhammi on 3/1/2020.
 */

internal class StackRemoteViewsFactory(
    private val mContext: Context, private val dataManager: DataManager
) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var mWidgetItems: List<Movie>

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        mWidgetItems = dataManager.getFavoriteMovieList()
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.item_widget)

        try {
            val bitmap = Glide.with(mContext)
                .asBitmap()
                .load(mWidgetItems[position].getPosterFullPath())
                .submit(512, 512)
                .get()

            rv.setImageViewBitmap(R.id.imageView, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val fillInIntent = Intent()

        if (mWidgetItems.isNotEmpty()) {
            val extras = bundleOf(FavoriteWidget.EXTRA_ITEM to mWidgetItems[position].title)
            fillInIntent.putExtras(extras)
        }

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}