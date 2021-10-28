package com.example.lastjfproject.storageData

import android.content.Context
import android.content.SharedPreferences




class UserSharePreference(context: Context) {
    private val PREF_FILE_NAME = "share_pref"

    var sharePreference = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)!!

    fun save(KEY_NAME: String, string: String){
        val editor: SharedPreferences.Editor = sharePreference.edit()
        editor.putString(KEY_NAME,string)
        editor.apply()
    }


    fun save(KEY_NAME: String, boolean: Boolean){
        val editor: SharedPreferences.Editor = sharePreference.edit()
        editor.putBoolean(KEY_NAME, boolean)
        editor.apply()
    }

    fun getPrefString(KEY_NAME: String) = sharePreference.getString(KEY_NAME,"")

    fun getPrefBoolean(KEY_NAME: String) = sharePreference.getBoolean(KEY_NAME,false)

    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharePreference.edit()

        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {

        val editor: SharedPreferences.Editor = sharePreference.edit()

        editor.remove(KEY_NAME)
        editor.apply()
    }
}