package com.dwirandyh.sharedpreferences.utils

import android.content.Context
import com.dwirandyh.sharedpreferences.model.UserModel

class UserPreference(context: Context) {

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AGE = "age"
        private const val PHONE_NUMBER = "phone"
        private const val LOVE = "isLove"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(user: UserModel) {
        val editor = preferences.edit()
        editor.putString(NAME, user.name)
        editor.putString(EMAIL, user.email)
        editor.putInt(AGE, user.age)
        editor.putString(PHONE_NUMBER, user.phoneNumber)
        editor.putBoolean(LOVE, user.isLove)
        editor.apply()
    }

    fun getUser(): UserModel {
        val model = UserModel()
        model.name = preferences.getString(NAME, "")
        model.email = preferences.getString(EMAIL, "")
        model.age = preferences.getInt(AGE, 0)
        model.phoneNumber = preferences.getString(PHONE_NUMBER, "")
        model.isLove = preferences.getBoolean(LOVE, false)
        return model
    }
}