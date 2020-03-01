package id.idham.moviecatalogue.widget

import android.content.Intent
import android.widget.RemoteViewsService
import org.koin.android.ext.android.inject

/**
 * Created by idhammi on 3/1/2020.
 */

class StackWidgetService : RemoteViewsService() {
    private val stackRemoteViewsFactory by inject<StackRemoteViewsFactory>()
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory = stackRemoteViewsFactory
}