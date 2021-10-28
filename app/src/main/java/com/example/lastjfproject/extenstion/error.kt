package com.example.lastjfproject.extenstion

class error {

    fun ERR_2_PASSWORD_NOT_SAME(pass1: String, pass2: String): Boolean {
        return pass1 == pass2
    }

    fun ERR_STRING_NULL(string: String) = string.isNullOrEmpty()


}