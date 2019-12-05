package com.dwirandyh.settingpreference

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var NAME: String
    private lateinit var EMAIL: String
    private lateinit var AGE: String
    private lateinit var PHONE: String
    private lateinit var LOVE: String

    private lateinit var namePreference: EditTextPreference
    private lateinit var emailPreference: EditTextPreference
    private lateinit var agePreference: EditTextPreference
    private lateinit var phonePreference: EditTextPreference
    private lateinit var isLovePreference: CheckBoxPreference

    companion object {
        private const val DEFAULT_VALUE = "Tidak ada"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        initPreference()
        setSummaries()
    }

    /**
     * Memanggil komponen seperti EditTExtPreference dan CheckboxPreference
     * Berdasarkan key yang sudah ditetapkan di preferences.xml
     *
     */
    private fun initPreference() {
        NAME = resources.getString(R.string.key_name)
        EMAIL = resources.getString(R.string.key_email)
        AGE = resources.getString(R.string.key_age)
        PHONE = resources.getString(R.string.key_phone)
        LOVE = resources.getString(R.string.key_love)

        namePreference = findPreference<EditTextPreference>(NAME) as EditTextPreference
        emailPreference = findPreference<EditTextPreference>(EMAIL) as EditTextPreference
        agePreference = findPreference<EditTextPreference>(AGE) as EditTextPreference
        phonePreference = findPreference<EditTextPreference>(PHONE) as EditTextPreference
        isLovePreference = findPreference<CheckBoxPreference>(LOVE) as CheckBoxPreference

    }

    /**
     * Digunakan untuk merubah value dari object EditTextPreference dan CheckboxPreference
     *
     */
    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        namePreference.summary = sh.getString(NAME, DEFAULT_VALUE)
        emailPreference.summary = sh.getString(EMAIL, DEFAULT_VALUE)
        agePreference.summary = sh.getString(AGE, DEFAULT_VALUE)
        phonePreference.summary = sh.getString(PHONE, DEFAULT_VALUE)
        isLovePreference.isChecked = sh.getBoolean(LOVE, false)
    }

    /**
     * Digunakan untuk meregister ketika dibuka
     *
     */
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    /**
     * Digunakan untuk me-unregister ketika aplikasi ditutup supaya listener tidak berjalan terus menerus
     * dan menyebabkan memory leak
     *
     */
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    /**
     * Ketika ada perubahan summary. jika ada perubahan maka akan memanggil listener onSharedPreferenceChanged
     *
     * @param sharedPreferences
     * @param key
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == NAME) {
            namePreference.summary = sharedPreferences?.getString(NAME, DEFAULT_VALUE)
        }
        if (key == EMAIL) {
            emailPreference.summary = sharedPreferences?.getString(EMAIL, DEFAULT_VALUE)
        }
        if (key == AGE) {
            agePreference.summary = sharedPreferences?.getString(AGE, DEFAULT_VALUE)
        }
        if (key == PHONE) {
            phonePreference.summary = sharedPreferences?.getString(PHONE, DEFAULT_VALUE)
        }
        if (key == LOVE) {
            isLovePreference.isChecked = sharedPreferences?.getBoolean(LOVE, false) ?: false
        }
    }

}