package com.bogdanandrei14.androidapp.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie (
    @PrimaryKey @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "nume") var nume: String,
    @ColumnInfo(name = "gen") var gen: String,
    @ColumnInfo(name = "an_aparitie") var an_aparitie: String,
    @ColumnInfo(name = "durata") var durata: String,
    @ColumnInfo(name = "descriere") var descriere: String
) {
    override fun toString(): String = nume
}