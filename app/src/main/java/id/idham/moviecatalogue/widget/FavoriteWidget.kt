package id.idham.moviecatalogue.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.extension.toast
import id.idham.moviecatalogue.service.StackWidgetService

/**
 * Implementation of App Widget functionality.
 */
class FavoriteWidget : AppWidgetProvider() {

    companion object {

        private const val TOAST_ACTION = "id.idham.moviecatalogue.TOAST_ACTION"
        const val EXTRA_ITEM = "id.idham.moviecatalogue.EXTRA_ITEM"

        private fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.favorite_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val toastIntent = Intent(context, FavoriteWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            val toastPendingIntent = PendingIntent.getBroadcast(
                context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == TOAST_ACTION) {
                val title = intent.getStringExtra(EXTRA_ITEM)
                context.toast(title as String)
            }
        }
    }
}

