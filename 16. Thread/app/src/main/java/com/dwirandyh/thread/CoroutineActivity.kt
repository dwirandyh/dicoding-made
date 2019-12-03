package com.dwirandyh.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*
import java.lang.Exception

class CoroutineActivity : AppCompatActivity() {

    companion object {
        private const val INPUT_STRING = "Halo Ini Demo AsyncTask!!"
        private const val LOG_ASYNC = "DemoAsync"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        tv_status.setText(R.string.status_pre)
        tv_desc.text = INPUT_STRING

        runCoroutine()
    }

    private fun runCoroutine() {
        GlobalScope.launch(Dispatchers.IO) {
            val input = INPUT_STRING

            var output: String? = null

            Log.d(LOG_ASYNC, "status : doInBackground")
            try {
                // append output with parameter from outside
                output = "$input Selamat Belajar!!"

                delay(5000)

                // move to main thread for updating UI
                withContext(Dispatchers.Main) {
                    tv_status.setText(R.string.status_post)
                    tv_desc.text = output
                }

            } catch (e: Exception) {
                Log.d(LOG_ASYNC, e.message.toString())
            }
        }
    }
}
