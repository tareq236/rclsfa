package com.impala.rclsfa.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)


    var userId: String?
        get() = sharedPreferences.getString("user_id", "")
        set(userId) = sharedPreferences.edit().putString("user_id", userId!!).apply()
    var designationId: Int?
        get() = sharedPreferences.getInt("designation_id", 0)
        set(designationId) = sharedPreferences.edit().putInt("designation_id", designationId!!).apply()


    var password: String?
        get() = sharedPreferences.getString("password", "")
        set(password) = sharedPreferences.edit().putString("password", password).apply()

    var isSaveLogin: Boolean?
        get() = sharedPreferences.getBoolean("is_save", false)
        set(isSaveLogin) = sharedPreferences.edit().putBoolean("is_save", isSaveLogin!!).apply()

    var email: String?
        get() = sharedPreferences.getString("email", "")
        set(email) = sharedPreferences.edit().putString("email", email).apply()
}