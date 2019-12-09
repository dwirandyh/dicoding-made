package com.dwirandyh.movieapp.reminder.release

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.model.network.NetworkService
import com.dwirandyh.movieapp.model.network.response.MovieListResponse
import retrofit2.Callback
import com.dwirandyh.movieapp.view.MainActivity
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReleaseReminderWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private var idNotification = 0

    private var movieList: List<Movie> = ArrayList()

    private var resultStatus: Result? = null

    companion object {
        val TAG = ReleaseReminderWorker::class.java.simpleName

        private const val CHANNEL_ID = "release_channel"
        private const val CHANNEL_NAME = "New Release Channel"
        private const val GROUP_KEY = "release_group_key"
        private const val MAX_NOTIFICATION = 3
        private const val NOTIFICATION_REQUEST_CODE = 200
    }

    override fun doWork(): Result {
        val result = getMovieReleaseToday()
        return result
    }

    private fun getMovieReleaseToday(): Result {
        val today = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val response =
            NetworkService.movieDBApi().getMovieReleasedToday(NetworkService.API_KEY, today, today)
                .execute()

        if (response.isSuccessful) {
            response.body()?.let {
                movieList = it.results
                movieList.forEach {
                    if (idNotification <= MAX_NOTIFICATION){
                        showNotification()
                        idNotification++
                    }
                }
            }
            return Result.success()
        } else {
            return Result.failure()
        }
    }

    private fun showNotification() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_notifications)

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val mBuilder: NotificationCompat.Builder

        if (idNotification < MAX_NOTIFICATION) {
            mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(movieList[idNotification].title)
                .setContentText("${movieList[idNotification].title} has been released today!")
                .setSmallIcon(R.drawable.ic_movie)
                .setLargeIcon(largeIcon)
                .setGroup(GROUP_KEY)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        } else {
            val inboxStyle = NotificationCompat.InboxStyle()
                .addLine("${movieList[idNotification].title} has been released today!")
                .addLine("${movieList[idNotification - 1].title} has been released today!")
                .addLine("${movieList[idNotification - 2].title} has been released today!")
                .setBigContentTitle("$idNotification new movies!")
                .setSummaryText("${movieList.size} movies has been released today!")

            mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("$idNotification new movie")
                .setContentText("New movie today")
                .setSmallIcon(R.drawable.ic_movie)
                .setGroup(GROUP_KEY)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setAutoCancel(true)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            mBuilder.setChannelId(CHANNEL_ID)

            notificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()

        notificationManager.notify(idNotification, notification)
    }
}