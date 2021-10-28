package com.example.lastjfproject.ui.catagorydetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lastjfproject.myObject.Book

class CatagoryDetailViewModel:ViewModel() {
    var mutableBook = MutableLiveData<Book>()

    fun initBook(book: Book){
        mutableBook.value = book
    }

    fun getBook() = mutableBook
}