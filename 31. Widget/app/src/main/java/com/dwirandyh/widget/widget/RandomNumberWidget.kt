package com.dwirandyh.widget.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.dwirandyh.widget.R

/**
 * Implementation of App Widget functionality.
 */
class RandomNumberWidget : AppWidgetProvider() {


    /**
     * Method yang di panggil ketika widget pertama kali dibuat
     * Method ini juga akan dijalankan ketika updatePeriodMillls yang di dalam random_numbers_widget_info.xml mencapai waktunya
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //region BUTTON CLICK IN WIDGET
    companion object {
        private const val WIDGET_CLICK = "widgetsclick"
        private const val WIDGET_ID_EXTRA = "widget_id_extra"
    }

    /**
     * Untuk memproses apa yang dibroadcast
     *
     * @param context
     * @param intent
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (WIDGET_CLICK == intent?.action) {
            val appWidgetmanager = AppWidgetManager.getInstance(context)
            val views = RemoteViews(context?.packageName, R.layout.random_number_widget)
            val lastUpdate = "Random: " + NumberGenerator.generate(100)
            val appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA, 0)
            views.setTextViewText(R.id.appwidget_text, lastUpdate)
            appWidgetmanager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun getPendingSelfIntent(
        context: Context,
        appWidgetId: Int,
        action: String
    ): PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = action
        // identifier untuk mengetahui widget mana yang ditekan
        intent.putExtra(WIDGET_ID_EXTRA, appWidgetId)
        return PendingIntent.getBroadcast(context, appWidgetId, intent, 0)
    }

    //endregion

    /**
     * Update content pada layout widget
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // Construct the RemoteViews object
        val views = RemoteViews(
            context.packageName,
            R.layout.random_number_widget
        )
        // Instruct the widget manager to update the widget
        val lastUpdate = "Random : ${NumberGenerator.generate(100)}"
        views.setTextViewText(R.id.appwidget_text, lastUpdate)

        // Set On Click
        views.setOnClickPendingIntent(R.id.btn_click, getPendingSelfIntent(context, appWidgetId, WIDGET_CLICK))

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}

