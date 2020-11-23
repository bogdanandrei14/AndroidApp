package com.bogdanandrei14.androidapp.auth.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class TokenHolder (
    val token: String
)

//@Entity(tableName = "tokens")
//data class TokenHolder (
//    @PrimaryKey @ColumnInfo(name = "token") val token: String
//)