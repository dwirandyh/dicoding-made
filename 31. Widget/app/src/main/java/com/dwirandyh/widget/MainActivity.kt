package com.dwirandyh.widget

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dwirandyh.widget.widget.scheduler.UpdateWidgetService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val JOB_ID = 100
        private const val SCHEDULE_OF_PERIOD = 86000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {
            startJob()
        }

        btn_stop.setOnClickListener {
            cancelJob()
        }
    }

    private fun startJob() {
        val mServiceComponent = ComponentName(this, UpdateWidgetService::class.java)

        val builder = JobInfo.Builder(JOB_ID, mServiceComponent)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(900000) // 15 menit
        } else {
            builder.setPeriodic(SCHEDULE_OF_PERIOD) // 3 menit
        }

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(builder.build())

        Toast.makeText(this, "Job service started", Toast.LENGTH_SHORT).show()
    }

    private fun cancelJob() {
        val tm = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        tm.cancel(JOB_ID)
        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show()
    }
}
