package com.dwirandyh.movieapp.widget

import android.content.Intent
import android.widget.RemoteViewsService

class FavoriteWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return FavoriteRemoteViewsFactory(this.applicationContext)
    }
}