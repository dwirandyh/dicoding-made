package com.dwirandyh.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import com.dwirandyh.workmanager.worker.MyWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOneTimeTask.setOnClickListener {
            startOneTimeTask()
        }

        btnPeriodicTask.setOnClickListener {
            startPeriodicTask()
        }

        btnCancelTask.setOnClickListener {
            cancelPeriodicTask()
        }
    }

    private fun startOneTimeTask() {
        textStatus.text = getString(R.string.status)
        // data yang akan dikirimkan ke worker
        val data = Data.Builder()
            .putString(MyWorker.EXTRA_CITY, editCity.text.toString())
            .build()

        // Kondisi worker akan berjalan
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueue(oneTimeWorkRequest)
        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this@MainActivity,
                Observer<WorkInfo> { workInfo ->
                    val status = workInfo.state.name
                    textStatus.append("\n $status")
                })
    }

    private fun startPeriodicTask() {
        textStatus.text = getString(R.string.status)

        // Dat ayang akan dkirimkan ke worker
        val data = Data.Builder()
            .putString(MyWorker.EXTRA_CITY, editCity.text.toString())
            .build()

        // Kondisi worker akan berjalan
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // repeat this task every 15 minutes
        periodicWorkRequest =
            PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                .setInputData(data)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueue(periodicWorkRequest)
        WorkManager.getInstance().getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(this@MainActivity, Observer<WorkInfo> { workInfo ->
                val status = workInfo.state.name
                textStatus.append("\n$status")
                btnCancelTask.isEnabled = false
                if (workInfo.state == WorkInfo.State.ENQUEUED) {
                    btnCancelTask.isEnabled = true
                }
            })
    }

    private fun cancelPeriodicTask() {
        WorkManager.getInstance().cancelWorkById(periodicWorkRequest.id)
    }
}
