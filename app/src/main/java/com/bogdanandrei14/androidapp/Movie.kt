package com.bogdanandrei14.androidapp

data class Movie (
    val id: String,
    var nume: String,
    var gen: String,
    var an_aparitie: String,
    var durata: String,
    var descriere: String
) {
    override fun toString(): String = nume
}