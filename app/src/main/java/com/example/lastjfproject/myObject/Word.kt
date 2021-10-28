package com.example.lastjfproject.myObject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myWord")
data class Word(val id: Int?=null,
                @PrimaryKey
                val name: String="",
                val imi: String?=null,
                val maucau: String?=null,
                val omoimasu: Boolean = false)
