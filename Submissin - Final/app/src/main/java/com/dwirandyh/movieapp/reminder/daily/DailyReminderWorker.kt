package com.dwirandyh.movieapp.reminder.daily

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.view.MainActivity

class DailyReminderWorker(private val context: Context, workParams: WorkerParameters) :
    Worker(context, workParams) {

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_daily_reminder"
        const val CHANNEL_NAME = "Daily Reminder Channel"

        val TAG = DailyReminderWorker::class.java.simpleName
    }

    override fun doWork(): Result {
        showNotification(
            applicationContext.getString(R.string.app_name),
            applicationContext.getString(R.string.notification_daily_reminder_text)
        )
        return Result.success()
    }

    private fun showNotification(title: String, description: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }
}