package com.example.lastjfproject.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lastjfproject.myObject.Word
import com.example.lastjfproject.ui.dowloaded.DowloadedWordFragment

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class roomDBLocal : RoomDatabase(){

    abstract fun myWordDAO(): WordDAO?

    companion object{
        private const val DATABASE_NAME = "myWord.db"
        private var instance: roomDBLocal? = null
        @Synchronized
        fun getInstance(context: Context): roomDBLocal? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    roomDBLocal::class.java,
                    DATABASE_NAME)
                    .allowMainThreadQueries().build()
            }
            return instance
        }
    }
}