package com.dwirandyh.broadcast.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.dwirandyh.broadcast.SmsReceiverActivity
import java.lang.Exception

class SmsReceiver : BroadcastReceiver() {

    private val TAG = SmsReceiver::class.java.simpleName

    private var context: Context? = null

    override fun onReceive(context: Context, intent: Intent) {
        this.context = context

        val bundle = intent.extras
        try {
            bundle?.let {
                readPduObject(bundle)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception smsReceiver$e")
        }
    }

    private fun readPduObject(bundle: Bundle) {
        val pdusObj = bundle.get("pdus") as Array<Any>
        for (aPdusObj in pdusObj) {
            val currentMessage = getIncomingMessage(aPdusObj, bundle)
            val senderNum = currentMessage.displayOriginatingAddress
            val message = currentMessage.displayMessageBody
            Log.d(TAG, "senderNum : $senderNum; message: $message")

            showSmsIntent(senderNum, message)
        }
    }

    private fun showSmsIntent(sender: String, sms: String) {
        context?.let {
            val showSmsIntent = Intent(it, SmsReceiverActivity::class.java)
            // FLAG_ACTIVITY_NEW_TASK
            // akan menjalankan activity pada task yang berbeda, bila activity tersebut sudah ada di dalam stack, maka ia akan ditampilkan kelayar
            showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_NO, sender)
            showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, sms)
            it.startActivity(showSmsIntent)
        }
    }

    private fun getIncomingMessage(aObject: Any, bundle: Bundle): SmsMessage {
        var currentSms: SmsMessage
        if (Build.VERSION.SDK_INT >= 23) {
            val format = bundle.getString("format")
            currentSms = SmsMessage.createFromPdu(aObject as ByteArray, format)
        } else {
            currentSms = SmsMessage.createFromPdu(aObject as ByteArray)
        }

        return currentSms
    }
}
