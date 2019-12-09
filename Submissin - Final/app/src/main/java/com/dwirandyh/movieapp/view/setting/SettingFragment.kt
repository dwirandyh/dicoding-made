package com.dwirandyh.movieapp.view.setting


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.work.*
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.reminder.daily.DailyReminderWorker
import com.dwirandyh.movieapp.reminder.release.ReleaseReminderWorker
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private var btnChangeLanguage: Preference? = null

    private lateinit var dailyReminder: String
    private lateinit var releaseReminder: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnChangeLanguage = findPreference(getString(R.string.key_change_language))
        btnChangeLanguage?.setSummary(Locale.getDefault().displayLanguage)
        btnChangeLanguage?.setOnPreferenceClickListener {
            openLanguageSetting()
            true
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_preferences)

        init()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == dailyReminder) {
            val selected = sharedPreferences?.getBoolean(dailyReminder, false)
            if (selected == true) {
                stopDailyReminder()
                scheduleDailyReminder(7, 0)
            } else {
                stopDailyReminder()
            }
        } else if (key == releaseReminder) {
            val selected = sharedPreferences?.getBoolean(releaseReminder, false)
            if (selected == true) {
                stopReleaseTodayRemainder()
                scheduleReleaseTodayRemainder(8, 0)
            } else {
                stopReleaseTodayRemainder()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun init() {
        dailyReminder = resources.getString(R.string.key_daily_reminder)
        releaseReminder = resources.getString(R.string.key_release_reminder)
    }


    private fun openLanguageSetting() {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }

    private fun scheduleDailyReminder(hour: Int, minute: Int) {

        val timeDelay = calculateTimeDelay(hour, minute)

        val mWorkManager = WorkManager.getInstance(requireContext())

        val mRequest =
            PeriodicWorkRequest.Builder(
                DailyReminderWorker::class.java,
                1,
                TimeUnit.DAYS
            ) // Periodic tiap 1 hari sekali
                .setInitialDelay(
                    timeDelay,
                    TimeUnit.MILLISECONDS
                ) // set delay agar sesuai dengan jam yang diinginkan
                .addTag(DailyReminderWorker.TAG)
                .build()

        mWorkManager.enqueue(mRequest)

    }

    private fun stopDailyReminder() {
        WorkManager.getInstance(requireContext()).cancelAllWorkByTag(DailyReminderWorker.TAG)
    }

    private fun scheduleReleaseTodayRemainder(hour: Int, minute: Int) {
        val timeDelay = calculateTimeDelay(hour, minute)

        val mWorkManager = WorkManager.getInstance(requireContext())

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val mRequest =
            PeriodicWorkRequest.Builder(ReleaseReminderWorker::class.java, 1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setInitialDelay(timeDelay, TimeUnit.MILLISECONDS)
                .addTag(ReleaseReminderWorker.TAG)
                .build()

        mWorkManager.enqueue(mRequest)
    }

    private fun stopReleaseTodayRemainder() {
        WorkManager.getInstance(requireContext()).cancelAllWorkByTag(ReleaseReminderWorker.TAG)
    }

    private fun calculateTimeDelay(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        val nowMillis = calendar.timeInMillis
        // jika jam saat ini lebih dari jam yang ingin di set misalkan jam 7 pagi
        if (calendar[Calendar.HOUR_OF_DAY] > hour ||
            calendar[Calendar.HOUR_OF_DAY] == hour && calendar[Calendar.MINUTE] >= minute
        ) {
            // tambahkan 1 hari ke vairbale calendar
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // set jam dan menit nya menjadi 7.00
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        // hitung berapa selisih waktu saat ini yang dibutuhkan untuk menuju hari esok pukul 7 pagi
        return calendar.timeInMillis - nowMillis
    }
}
