package com.example.calculator.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface CalHistoryDAO {
    @Query("SELECT * FROM CalHistory ORDER BY date DESC LIMIT :limit OFFSET :offset")
    fun getCalHistories(limit: Int, offset: Int): Flow<List<CalHistory>>

    @Query("SELECT * FROM CalHistory WHERE id = :id")
    suspend fun getCalHistoryById(id: Int): CalHistory?

    @Delete
    suspend fun delete(calHistory: CalHistory)

    @Insert
    suspend fun insert(calHistory: CalHistory)

    @Update
    suspend fun update(calHistory: CalHistory)
}