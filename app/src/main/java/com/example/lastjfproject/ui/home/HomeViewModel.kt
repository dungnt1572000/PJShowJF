package com.example.lastjfproject.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private var name_of_book = MutableLiveData<String>()

    fun create_Name_of_book_byString(str:String){
        name_of_book.value = str
    }

    fun getName_of_book() = name_of_book
}