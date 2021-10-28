package com.example.lastjfproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lastjfproject.myObject.CurrentUser
import com.example.lastjfproject.storageData.DatalocalSharePrefManager
import com.example.lastjfproject.ui.SlashFragment

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        if(checkifAccountLogined()){
            viewModel.getUserfromViewModel().observe(this, {
                CurrentUser.initUser(it)
                Log.e("CurrentUser ", it.email.toString())
            })
        }else{
            CurrentUser.deleteUser()
        }


        supportFragmentManager.beginTransaction()
            .replace(R.id.contain_activity_layout, SlashFragment())
            .setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out)
            .addToBackStack("ProcessFrag")
            .commit()
    }

    private fun checkifAccountLogined() :Boolean{
        if (DatalocalSharePrefManager.getUserLoginedSharePref()) {
            viewModel.getUserfromFirebase(DatalocalSharePrefManager.getUserNamefromSharePref()
                .toString(), DatalocalSharePrefManager.getUserPassfromSharePref().toString())
            return true
        }
        return false
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount >= 1)
            supportFragmentManager.popBackStack()
        else
            super.onBackPressed()
    }
}