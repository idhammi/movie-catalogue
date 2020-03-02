package id.idham.moviecatalogue.service

import android.content.Intent
import android.widget.RemoteViewsService
import id.idham.moviecatalogue.widget.StackRemoteViewsFactory
import org.koin.android.ext.android.inject

/**
 * Created by idhammi on 3/1/2020.
 */

class StackWidgetService : RemoteViewsService() {
    private val stackRemoteViewsFactory by inject<StackRemoteViewsFactory>()
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory = stackRemoteViewsFactory
}