package com.example.lastjfproject.myObject

import com.example.lastjfproject.user.User

object CurrentUser {
    private var user:User?=null
    fun initUser(user: User){
        this.user = user
    }

    fun getUser() =this.user

    fun deleteUser(){
        this.user = null;
    }
}