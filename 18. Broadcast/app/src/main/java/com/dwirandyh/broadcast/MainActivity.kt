package com.dwirandyh.broadcast

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dwirandyh.broadcast.service.DownloadService
import com.dwirandyh.broadcast.utils.PermissionManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val ACTION_DOWNLOAD_STATUS = "download_status"
        private const val SMS_REQUEST_CODE = 101
    }

    private lateinit var downloadReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_permission.setOnClickListener {
            askSmsPermission()
        }

        btn_download.setOnClickListener {
            startDownloadService()
        }

        registerReceiver()
    }

    //region BroadcastReceiver Case 1 for reading incoming sms
    private fun askSmsPermission() {
        PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, SMS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == SMS_REQUEST_CODE) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> Toast.makeText(
                    this,
                    "Sms receiver permission diterima",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Toast.makeText(
                    this,
                    "Sms receiver permission ditolak",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    //endregion


    private fun registerReceiver() {
        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d(DownloadService.TAG, "Download Selesai")
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }
        }

        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)

        registerReceiver(downloadReceiver, downloadIntentFilter)
    }

    private fun startDownloadService() {
        val downloadServiceIntent = Intent(this, DownloadService::class.java)
        startService(downloadServiceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Prevent memory leaks
        unregisterReceiver(downloadReceiver)
    }
}
