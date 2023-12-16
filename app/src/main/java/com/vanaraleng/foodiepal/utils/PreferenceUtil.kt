package com.vanaraleng.foodiepal.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences("FoodiePie",  MODE_PRIVATE)
    }

    fun setString(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String) : String? {
        return sharedPreferences.getString(key, null)
    }

    fun setBool(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBool(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

}