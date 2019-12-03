package com.dwirandyh.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.dwirandyh.service.service.MyBoundService
import com.dwirandyh.service.service.MyIntentService
import com.dwirandyh.service.service.MyService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start_service.setOnClickListener {
            startService()
        }

        btn_start_intent_service.setOnClickListener {
            startIntentService()
        }

        btn_start_bound_service.setOnClickListener {
            startBoundService()
        }

        btn_stop_bound_service.setOnClickListener {
            stopBoundService()
        }
    }

    private fun startService() {
        val mStartServiceIntent = Intent(this@MainActivity, MyService::class.java)
        startService(mStartServiceIntent)
    }

    private fun startIntentService() {
        val startIntentService = Intent(this@MainActivity, MyIntentService::class.java)
        startIntentService.putExtra(MyIntentService.EXTRA_DURATION, 5000L)
        startService(startIntentService)
    }


    //region BOUND SERVICE
    private var mSErviceBound = false
    private lateinit var mBoundService: MyBoundService

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            mSErviceBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBoundService.MyBinder
            mBoundService = myBinder.getSErvice
            mSErviceBound = true
        }

    }

    private fun startBoundService() {
        val boundServiceIntent = Intent(this@MainActivity, MyBoundService::class.java)
        bindService(boundServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopBoundService() {
        unbindService(mServiceConnection)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mSErviceBound) {
            unbindService(mServiceConnection)
        }
    }
    //endregion
}
