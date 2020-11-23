package com.bogdanandrei14.androidapp.auth.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bogdanandrei14.androidapp.todo.data.Movie

@Dao
interface MovieDao {
    @Query("SELECT * from movies ORDER BY nume ASC")
    fun getAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE _id=:id ")
    fun getById(id: String): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(movie: Movie)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}