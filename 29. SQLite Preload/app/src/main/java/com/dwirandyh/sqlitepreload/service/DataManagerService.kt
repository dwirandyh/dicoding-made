package com.dwirandyh.sqlitepreload.service

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.dwirandyh.sqlitepreload.database.MahasiswaHelper
import com.dwirandyh.sqlitepreload.model.MahasiswaModel
import com.dwirandyh.sqlitepreload.utils.AppPreference
import java.io.BufferedReader
import java.lang.Exception
import com.dwirandyh.sqlitepreload.R
import kotlinx.coroutines.*
import java.io.InputStreamReader
import kotlin.coroutines.CoroutineContext

class DataManagerService : Service(), CoroutineScope {

    private val TAG = DataManagerService::class.java.simpleName
    private var mActivityMessenger: Messenger? = null

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    companion object {
        const val PREPARATION_MESSAGE = 0
        const val UPDATE_MESSAGE = 1
        const val SUCCESS_MESSAGE = 2
        const val FAILED_MESSAGE = 3
        const val CANCEL_MESSAGE = 4
        const val ACTIVITY_HANDLER = "activity_handler"
        private const val MAX_PROGRESS = 100.0
    }

    /**
     * Method yang akan dipanggil ketika service di bind(diikatkan ke activity
     *
     * @param intent
     * @return
     */
    override fun onBind(intent: Intent): IBinder? {
        mActivityMessenger = intent.getParcelableExtra(ACTIVITY_HANDLER)

        // proses ambil data
        loadDataAsync()

        return mActivityMessenger.let { it?.binder }
    }

    /**
     * Method yang akan di panggil ketika service dileas dari activity
     *
     * @param intent
     * @return
     */
    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        job.cancel()
        return super.onUnbind(intent)
    }

    /**
     * Method yang akan dipanggil ketika service diikatkan kembali
     *
     * @param intent
     */
    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind :")
    }

    override fun onCreate() {
        super.onCreate()
        job = Job() // create the job kotlin coroutine
        Log.d(TAG, "onCreate : ")
    }

    /**
     * Ketika semua bind sudah dilepas maka onDestroy akan terpanggil otomatis
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        Log.d(TAG, "onDestroy : ")
    }

    private fun loadDataAsync() {
        sendMessage(PREPARATION_MESSAGE)
        job = launch {
            val isInsertSuccess = async(Dispatchers.IO) {
                getData()
            }

            if (isInsertSuccess.await()) {
                sendMessage(SUCCESS_MESSAGE)
            } else {
                sendMessage(FAILED_MESSAGE)
            }
        }
    }

    private fun sendMessage(messageStatus: Int) {
        val message = Message.obtain(null, messageStatus)
        try {
            mActivityMessenger?.send(message)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }


    fun getData(): Boolean {
        val mahasiswaHelper = MahasiswaHelper.getInstance(applicationContext)
        val appPreference = AppPreference(applicationContext)

        val firstRun = appPreference.firstRun as Boolean

        /**
         * Jika first run true maka akan melakukan proses pre load,
         * Jika first run false maka akan langsung menuju home
         */
        if (firstRun) {
            /**
             * Load raw data dari file txt ke dalam array model mahasiswa
             */
            val mahasiswaModels = preLoadRaw()

            mahasiswaHelper.open()

            var progress = 30.0
            publishProgress(progress.toInt())
            val progressMaxInsert = 80.0
            val progressDiff = (progressMaxInsert - progress) / mahasiswaModels.size

            var isInsertSuccess: Boolean

            // Gunakan ini untuk insert query dengan menggunakan standar query
            try {
                mahasiswaHelper.beginTransaction() // memulai untuk transaction

                for (model in mahasiswaModels) {
                    if (job.isCancelled) { // jika di cancel atau user memencet tombol back saat proses belum selesai
                        break
                    } else {
                        // insert biasa tidak menggunakan transaction
//                        mahasiswaHelper.insert(model)

                        //insert dengan transactional
                        mahasiswaHelper.insertTransaction(model)
                        progress += progressDiff
                        publishProgress(progress.toInt())
                    }
                }

                if (job.isCancelled) { // jika di cancel atau user memencet tombol back saat proses belum selesai
                    isInsertSuccess = false
                    appPreference.firstRun = true
                    sendMessage(CANCEL_MESSAGE)
                } else {
                    mahasiswaHelper.setTransactionSuccess()
                    isInsertSuccess = true

                    appPreference.firstRun = false
                }
            } catch (e: Exception) {
                Log.e(TAG, "doInBackground :Exception")
                isInsertSuccess = false
            } finally {
                mahasiswaHelper.endTransaction()
            }

            // Close helper ketika proses query sudah selesai
            mahasiswaHelper.close()

            publishProgress(MAX_PROGRESS.toInt())

            return isInsertSuccess
        } else {
            try {
                synchronized(this) {
                    publishProgress(50)
                    publishProgress(MAX_PROGRESS.toInt())
                    return true
                }
            } catch (e: Exception) {
                return false
            }
        }
    }

    private fun publishProgress(progress: Int) {
        try {
            val message = Message.obtain(
                null,
                UPDATE_MESSAGE
            )
            val bundle = Bundle()
            bundle.putLong("KEY_PROGRESS", progress.toLong())
            message.data = bundle
            mActivityMessenger?.send(message)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    private fun preLoadRaw(): ArrayList<MahasiswaModel> {
        val mahasiswaModels = ArrayList<MahasiswaModel>()
        var line: String?
        val reader: BufferedReader
        try {
            val rawtext = resources.openRawResource(R.raw.data_mahasiswa)
            reader = BufferedReader(InputStreamReader(rawtext))
            do {
                line = reader.readLine()
                // split string dengan \t atau tab
                val splitStr = line.split("\t".toRegex()).dropLastWhile {
                    it.isEmpty()
                }.toTypedArray()

                val mahasiswaModel = MahasiswaModel()
                mahasiswaModel.name = splitStr[0]
                mahasiswaModel.nim = splitStr[1]
                mahasiswaModels.add(mahasiswaModel)
            } while (line != null)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return mahasiswaModels
    }
}
