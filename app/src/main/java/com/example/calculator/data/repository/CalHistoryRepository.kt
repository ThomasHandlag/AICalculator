package com.example.calculator.data.repository

import com.example.calculator.data.database.CalHistory
import com.example.calculator.data.database.CalHistoryDAO
import kotlinx.coroutines.flow.Flow

interface CalHistoryRepository {
    fun getCalHistories(
        limit: Int,
        offset: Int
    ): Flow<List<CalHistory>>

    suspend fun getCalHistoryById(id: Int): CalHistory?

    suspend fun deleteCalHistory(calHistory: CalHistory)

    suspend fun insertCalHistory(calHistory: CalHistory)

    suspend fun updateCalHistory(calHistory: CalHistory)
}

class CalHistoryRepositoryImpl(
    val calHistoryDAO: CalHistoryDAO
) : CalHistoryRepository {

    override fun getCalHistories(
        limit: Int,
        offset: Int
    ): Flow<List<CalHistory>> {
        return calHistoryDAO.getCalHistories(limit, offset)
    }

    override suspend fun getCalHistoryById(id: Int): CalHistory? {
        return calHistoryDAO.getCalHistoryById(id)
    }

    override suspend fun deleteCalHistory(calHistory: CalHistory) {
        calHistoryDAO.delete(calHistory)
    }

    override suspend fun insertCalHistory(calHistory: CalHistory) {
        calHistoryDAO.insert(calHistory)
    }

    override suspend fun updateCalHistory(calHistory: CalHistory) {
        calHistoryDAO.update(calHistory)
    }
}