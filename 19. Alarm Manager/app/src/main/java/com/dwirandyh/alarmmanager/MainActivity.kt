package com.dwirandyh.alarmmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import com.dwirandyh.alarmmanager.broadcast.AlarmReceiver
import com.dwirandyh.alarmmanager.dialog.DatePickerFragment
import com.dwirandyh.alarmmanager.dialog.TimePickerFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener,
    TimePickerFragment.DialogTimeListener {

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_once_date.setOnClickListener {
            showDateDialog()
        }

        btn_once_time.setOnClickListener {
            showTimeDialog()
        }

        btn_set_once_alarm.setOnClickListener {
            setOnceAlarm()
        }

        alarmReceiver = AlarmReceiver()
    }

    private fun showDateDialog() {
        val datePickerFragment = DatePickerFragment()
        datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
    }

    private fun showTimeDialog() {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
    }

    private fun setOnceAlarm() {
        val onceDate = tv_once_date.text.toString()
        val onceTime = tv_once_time.text.toString()
        val onceMessage = edt_once_message.text.toString()

        alarmReceiver.setOnTimeAlarm(
            this,
            AlarmReceiver.TYPE_ONE_TIME,
            onceDate,
            onceTime,
            onceMessage
        )
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        tv_once_date.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        if (tag == TIME_PICKER_ONCE_TAG) {
            tv_once_time.text = dateFormat.format(calendar.time)
        }
    }
}
