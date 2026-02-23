package com.example.calculator.ui.viewModel

import androidx.compose.ui.platform.Clipboard
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.data.database.CalHistory
import com.example.calculator.data.repository.CalHistoryRepository
import com.example.calculator.exprcal.evaluate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

data class CalItemUIState(val expression: String, val result: String, val id: Int)

class BasicViewModel(val repo: CalHistoryRepository) : ViewModel() {

    private val _calState = MutableStateFlow(CalItemUIState("", "", -1))

    private val _mode = MutableStateFlow(false)
    private val _index = MutableStateFlow(expression.value.length)
    private val _tokenIndex = MutableStateFlow<Int?>(null)

    val tokenIndex: StateFlow<Int?> get() = _tokenIndex.asStateFlow()
    val calState: StateFlow<CalItemUIState> get() = _calState.asStateFlow()
    val expression: StateFlow<String>
        get() = calState.map { it.expression }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, ""
        )
    val result: StateFlow<String>
        get() = calState.map { it.result }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, ""
        )
    val mode: StateFlow<Boolean> get() = _mode.asStateFlow()
    val index: StateFlow<Int> get() = _index.asStateFlow()

    fun updateExpression(value: String) {
        _calState.value = _calState.value.copy(expression = value)
    }

    fun setTokenIndex(newTokenIndex: Int?) {
        _tokenIndex.value = newTokenIndex
    }

    fun calculate() {
        _calState.value = _calState.value.copy(result = evaluate(expression.value).toString())
        viewModelScope.launch {
            if (calState.value.id != -1)
                repo.updateCalHistory(
                    CalHistory(
                        id = calState.value.id,
                        expression = calState.value.expression,
                        result = calState.value.result,
                        date = Date().toString()
                    )
                )
            else {
                val entry = CalHistory(
                    id = Date().time.toInt(),
                    expression = calState.value.expression,
                    result = calState.value.result,
                    date = Date().toString()
                )
                repo.insertCalHistory(entry)
            }
        }
    }

    fun pasteFromClipboard(clipboard: Clipboard, index: Int) {
        viewModelScope.launch {
            val clipData = clipboard.getClipEntry()
            if (clipData != null) {
                val value = clipData.clipData.getItemAt(0).text
                val oldExp = expression.value
                if (index > oldExp.length) {
                    updateExpression(oldExp + value)
                } else {
                    updateExpression(oldExp.take(index) + value + oldExp.substring(index))
                }
            }
        }
    }

    fun initState(id: Int) {
        viewModelScope.launch {
            val calHistory = repo.getCalHistoryById(id)
            if (calHistory != null) {
                _calState.value = CalItemUIState(
                    id = calHistory.id,
                    expression = calHistory.expression,
                    result = calHistory.result
                )
            }
            updateMarkIndex(expression.value.length)
            _tokenIndex.value = null
        }
    }

    fun setMode() {
        _mode.value = !_mode.value
    }

    fun updateMarkIndex(newIndex: Int) {
        _index.value = newIndex
    }

    fun clear() {
        _calState.value = _calState.value.copy(expression = "", result = "", id = -1)
        updateExpression("")
        updateMarkIndex(0)
        _tokenIndex.value = null
    }
}