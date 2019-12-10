package com.dwirandyh.movieapp.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.FutureTarget
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.utils.MappingHelper
import java.lang.Exception


/**
 * Sebagai adapter untuk stackview
 *
 */
class FavoriteRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {


    companion object {
        val TAG = FavoriteRemoteViewsFactory::class.java.simpleName
    }

    private var widgetItems: List<Movie> = ArrayList()


    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    private var cursor: Cursor? = null

    override fun onDataSetChanged() {

        cursor?.close()

        val identityToken = Binder.clearCallingIdentity()

        cursor =
            context.contentResolver.query(Movie.CONTENT_URI, null, null, null, null, null)

        cursor?.let {
            val movieList = MappingHelper.mapCursorToMovieArrayList(it)
            widgetItems = movieList
        }

        Log.d("Favorite", "load data")

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val movie = widgetItems[position]

        val rv = RemoteViews(context.packageName, R.layout.favorite_widget_item)

        var bmp: Bitmap? = null
        try {
            val futureBitmap: FutureTarget<Bitmap> = Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load("https://image.tmdb.org/t/p/w185${movie.posterPath}")
                .submit()

            bmp = futureBitmap.get()
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        rv.setImageViewBitmap(R.id.imageView, bmp)


        val extras = Bundle()
        extras.putInt(FavoriteWidgetProvider.EXTRA_ITEM, position)

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int {
        return widgetItems.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

}