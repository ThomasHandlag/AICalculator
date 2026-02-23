package com.example.calculator.ui.viewModel

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.data.repository.CalHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class HistoryViewModel(val repo: CalHistoryRepository) : ViewModel() {

    val historyList: StateFlow<List<CalHistoryData>>
        get() = getCalHistory().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000),
            initialValue = emptyList()
        )

    fun getCalHistory(): Flow<List<CalHistoryData>> {
        return repo.getCalHistories(
            limit = 50,
            offset = 0
        ).map { calHistories ->
            calHistories.map { calHistory ->
                CalHistoryData(
                    id = calHistory.id,
                    expression = calHistory.expression,
                    result = calHistory.result,
                    timestamp = Date(calHistory.date)
                )
            }
        }
    }

    fun deleteHistoryItem(id: Int) {
        viewModelScope.launch {
            val calHistory = repo.getCalHistoryById(id)
            if (calHistory != null) {
                repo.deleteCalHistory(calHistory)
            }
        }
    }

    fun copyToClipboard(result: String, clipboard: Clipboard) {
        viewModelScope.launch {
            clipboard.setClipEntry(
                ClipEntry(
                    ClipData.newPlainText(
                        "Calculation Result",
                        result
                    )
                )
            )

        }
    }
}

data class CalHistoryData(
    val id: Int,
    val expression: String = "",
    val result: String = "",
    val timestamp: Date
)