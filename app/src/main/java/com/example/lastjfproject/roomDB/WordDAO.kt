package com.example.lastjfproject.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.lastjfproject.myObject.Word

@Dao
interface WordDAO {
    @Insert(onConflict = REPLACE)
    fun insertWord(word: Word)

    @Query("select * from myWord")
    fun getAllword() : List<Word>

    @Delete
    fun deleteTuvung(word: Word)

    @Query("Select * from myWord where name like '%' || :wordName || '%' ")
    infix fun findlocaltuvung(wordName:String): List<Word>
}