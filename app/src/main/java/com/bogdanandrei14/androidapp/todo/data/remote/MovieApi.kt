package com.bogdanandrei14.androidapp.todo.data.remote

import com.bogdanandrei14.androidapp.core.Api
import com.bogdanandrei14.androidapp.todo.data.Movie
import retrofit2.http.*

object MovieApi {
    interface Service {
        @GET("/api/movie")
        suspend fun find(): List<Movie>

        @GET("/api/movie/{id}")
        suspend fun read(@Path("id") movieId: String): Movie;

        @Headers("Content-Type: application/json")

        @POST("/api/movie")
        suspend fun create(@Body movie: Movie): Movie

        @Headers("Content-Type: application/json")
        @PUT("/api/movie/{id}")
        suspend fun update(@Path("id") movieId: String, @Body movie: Movie): Movie
    }

    val service: Service = Api.retrofit.create(Service::class.java)

}