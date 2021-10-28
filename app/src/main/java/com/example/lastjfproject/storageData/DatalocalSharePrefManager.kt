package com.example.lastjfproject.storageData

import android.annotation.SuppressLint
import android.content.Context

object DatalocalSharePrefManager  {

    private val PREF_USER_NAME = "user_name"
    private val PREF_USER_PASS = "user_pass"
    private val PREF_USER_SAVE = "user_save"
    @SuppressLint("StaticFieldLeak")
    private lateinit var userSharePreference :UserSharePreference
    fun init(context: Context){
        userSharePreference = UserSharePreference(context)
    }

    fun saveStringUserName(string: String){
        this.userSharePreference.save(PREF_USER_NAME,string)
    }

    fun saveStringUserPass(string: String){
        this.userSharePreference.save(PREF_USER_PASS,string)
    }
    fun saveBooleanUserSaved(boolean: Boolean){
        this.userSharePreference.save(PREF_USER_SAVE,boolean)
    }

    fun getUserNamefromSharePref() = this.userSharePreference.getPrefString(PREF_USER_NAME)
    fun getUserPassfromSharePref() = this.userSharePreference.getPrefString(PREF_USER_PASS)
    fun getUserLoginedSharePref() = this.userSharePreference.getPrefBoolean(PREF_USER_SAVE)
}