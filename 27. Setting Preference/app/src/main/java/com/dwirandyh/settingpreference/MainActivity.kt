package com.dwirandyh.settingpreference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Untuk menampilkan fragment setting ke activity
        supportFragmentManager.beginTransaction()
            .add(R.id.setting_holder, MyPreferenceFragment())
            .commit()
    }
}
