package com.example.androidapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object PrefsHelper {
    private const val PREFS_NAME = "AppPrefs"
    private const val KEY_LAST_EMAIL = "last_email"

    fun saveLastEmail(context: Context, email: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putString(KEY_LAST_EMAIL, email) }
    }

    fun getLastEmail(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LAST_EMAIL, "Unknown Player") ?: "Unknow Player"
    }
}