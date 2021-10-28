package com.example.lastjfproject.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lastjfproject.user.User

class SettingFragmentViewModel: ViewModel() {

    var mutableUser = MutableLiveData<User>()

    fun getUserfromFirebase(userName: String, userPass: String){

    }
}