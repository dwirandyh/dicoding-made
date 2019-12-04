package com.dwirandyh.alarmmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            showTimePicker(TIME_PICKER_ONCE_TAG)
        }

        btn_set_once_alarm.setOnClickListener {
            setOnceAlarm()
        }

        btn_repeating_time.setOnClickListener {
            showTimePicker(TIME_PICKER_REPEAT_TAG)
        }

        btn_set_repeating_alarm.setOnClickListener {
            setRepeatingAlarm()
        }

        btn_cancel_repeating_alarm.setOnClickListener {
            cancelAlarm()
        }

        alarmReceiver = AlarmReceiver()
    }

    private fun showDateDialog() {
        val datePickerFragment = DatePickerFragment()
        datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
    }

    private fun showTimePicker(tag: String) {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, tag)
    }

    private fun setOnceAlarm() {
        val onceDate = tv_once_date.text.toString()
        val onceTime = tv_once_time.text.toString()
        val onceMessage = edt_once_message.text.toString()

        alarmReceiver.setOneTimeAlarm(
            this,
            AlarmReceiver.TYPE_ONE_TIME,
            onceDate,
            onceTime,
            onceMessage
        )
    }

    private fun setRepeatingAlarm() {
        val repeatTime = tv_repeating_time.text.toString()
        val repeatMessage = edt_repeating_message.text.toString()
        alarmReceiver.setRepeatingAlarm(
            this,
            AlarmReceiver.TYPE_REPEATING,
            repeatTime,
            repeatMessage
        )
    }

    private fun cancelAlarm() {
        alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
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
        } else if (tag == TIME_PICKER_REPEAT_TAG) {
            tv_repeating_time.text = dateFormat.format(calendar.time)
        }
    }
}
