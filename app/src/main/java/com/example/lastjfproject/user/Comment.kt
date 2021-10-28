package com.example.lastjfproject.user

data class Comment(val cmt:String?=null,val time:String?=null, var viewType:Int = 2, var user: User?=null) {
}