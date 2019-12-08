package com.dwirandyh.stackviewwidget.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.dwirandyh.stackviewwidget.R

class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    /**
     * method ini dapat digunakan untuk memuat semua data yang ingin digunakan pada widget. proses load harus kurang dari 20 detik
     * jika tidak maka akan terjadi ANR (Application Not Responding)
     *
     */
    override fun onDataSetChanged() {
        // berfungsi untuk melakukan refresh saat terjadi perubahan
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.darth_vader))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources,R.drawable.star_wars_logo))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.storm_trooper))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.starwars))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.falcon))

        /**
         * Untuk menambahkan data dari database tambahkan kode dibawah ini agar tidak force close dan data
         * akan muncul secara real time
        if (cursor != null) {
            cursor.close()
        }

        val identityToken = Binder.clearCallingIdentity()

        // querying ke database
        cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null)

        Binder.restoreCallingIdentity(identityToken)
        */
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    /**
     * Memasang item yang berisikan imageview . yang akan ditampilkan oleh widget
     *
     * @param position
     * @return
     */
    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])

        val extras = Bundle()
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position)
        val fillIntent = Intent()
        fillIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent)
        return rv
    }

    /**
     * Harus mengembalikan nilai jumlah isi dari data yang ingin ditampilkan
     *
     * @return
     */
    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
    }

}