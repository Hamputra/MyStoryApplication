package com.example.mystoryapplication.pref

import android.content.Context

class UserPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveSession(token: String, isLogin: Boolean, name: String) {
        preferences.edit()
            .putString(TOKEN_KEY, token)
            .putBoolean(IS_LOGIN_KEY, isLogin)
            .putString(NAME_KEY, name)
            .apply()
    }

    fun getSession(): UserSession {
        val token = preferences.getString(TOKEN_KEY, "") ?: ""
        val isLogin = preferences.getBoolean(IS_LOGIN_KEY, false)
        val name = preferences.getString(NAME_KEY, "") ?: ""
        return UserSession(token, isLogin, name)
    }

    fun logout() {
        preferences.edit()
            .clear()
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val TOKEN_KEY = "token"
        private const val IS_LOGIN_KEY = "is_login"
        private const val NAME_KEY = "name"
    }
}

data class UserSession(
    val token: String,
    val isLogin: Boolean,
    val name: String,
)