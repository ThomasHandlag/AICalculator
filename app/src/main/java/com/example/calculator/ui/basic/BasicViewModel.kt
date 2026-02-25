package com.example.calculator.ui.basic

import android.util.Log
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

// ViewModel for the basic calculator screen.
class BasicViewModel(val repo: CalHistoryRepository) : ViewModel() {

    // This list is used to store the tokens of the current expression for display purposes.
    private val _expressionList = MutableStateFlow(mutableListOf<TokenData>())
    val expressionListState: StateFlow<List<TokenData>> get() = _expressionList.asStateFlow()

    val expStr = _expressionList.map { list ->
        list.joinToString("") { it.token }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), "")

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> get() = _result.asStateFlow()

    private val _historyId = MutableStateFlow(-1)
    val historyId: StateFlow<Int> get() = _historyId.asStateFlow()

    private val _mode = MutableStateFlow(false)
    val mode: StateFlow<Boolean> get() = _mode.asStateFlow()

    private val _selectTokenId = MutableStateFlow<Long?>(null)
    val selectedTokenId: StateFlow<Long?> get() = _selectTokenId.asStateFlow()

    fun updateToken(value: String, id: Long) {
        val currentList = _expressionList.value.toMutableList()
        val index = currentList.indexOfFirst { it.tokenId == id }
        if (index != -1) {
            currentList[index] = TokenData(value, id)
        } else {
            currentList.add(TokenData(value, id))
        }
        _expressionList.value = currentList
    }

    fun popToken() {
        val currentList = _expressionList.value.toMutableList()
        if (currentList.isNotEmpty()) {
            if (_selectTokenId.value != null) {
                val index = currentList.indexOfFirst { it.tokenId == _selectTokenId.value }
                if (index != -1 && index != 0) {
                    currentList.removeAt(index)
                    _expressionList.value = currentList
                    setSelectedTokenId(null)
                }
            } else {
                currentList.removeAt(currentList.size - 1)
                _expressionList.value = currentList
            }
        }
    }

    fun pushTokens(value: List<String>) {
        val currentList = _expressionList.value.toMutableList()
        val newId = System.currentTimeMillis()
        if (selectedTokenId.value != null) {
            val index = currentList.indexOfFirst { it.tokenId == selectedTokenId.value }
            if (index != -1) {
                currentList.removeAt(index)
                value.forEachIndexed { i, token ->
                    currentList.add(index + i, TokenData(token, newId + i))
                }
            }
        } else {
            value.forEachIndexed { i, token ->
                currentList.add(TokenData(token, newId + i))
            }
        }
        _expressionList.value = currentList
    }

    fun setSelectedTokenId(id: Long?) {
        _selectTokenId.value = id
    }

    fun calculate() {
        viewModelScope.launch {
            val expression = _expressionList.value.joinToString("") { it.token }
            _result.value = evaluate(expression).toString()
            Log.d(
                "BasicViewModel",
                "Calculated result: ${result.value} with expression: $expression"
            )
            if (historyId.value != -1)
                repo.updateCalHistory(
                    CalHistory(
                        id = historyId.value,
                        expression = expression,
                        result = result.value,
                        date = Date().toString()
                    )
                )
            else {
                val entry = CalHistory(
                    id = Date().time.toInt(),
                    expression = expression,
                    result = result.value,
                    date = Date().toString()
                )
                repo.insertCalHistory(entry)
            }
        }
    }

    fun pasteFromClipboard(clipboard: Clipboard) {
        viewModelScope.launch {
            val clipData = clipboard.getClipEntry()
            if (clipData != null) {
                val value = clipData.clipData.getItemAt(0).text
                val currentSelectedId = selectedTokenId.value
                if (currentSelectedId != null) {
                    updateToken(value.toString(), currentSelectedId)
                } else {
                    val newId = System.currentTimeMillis()
                    updateToken(value.toString(), newId)
                    setSelectedTokenId(newId)
                }
            }
        }
    }

    fun initState(id: Int) {
        viewModelScope.launch {
            val calHistory = repo.getCalHistoryById(id)
            if (calHistory != null) {
                _historyId.value = calHistory.id
                _expressionList.value =
                    parseExpression(calHistory.expression) as MutableList<TokenData>
                _result.value = calHistory.result
            }
            _selectTokenId.value = null
        }
    }

    fun setMode() {
        _mode.value = !_mode.value
    }


    fun clear() {
        _historyId.value = -1
        _expressionList.value = mutableListOf()
        _result.value = ""
        _selectTokenId.value = null
    }

    fun parseExpression(expression: String): List<TokenData> {
        if (expression.isEmpty()) return emptyList()

        val tokens = mutableListOf<TokenData>()
        val operators = setOf('+', '-', 'x', '×', '÷', '/', '%', '(', ')', '^', '√', '∛')
        val currentNumber = StringBuilder()

        var i = 0
        while (i < expression.length) {
            val char = expression[i]

            if (i + 2 < expression.length) {
                val threeChar = expression.substring(i, i + 3)
                if (threeChar in setOf("sin", "cos", "tan", "log")) {
                    if (currentNumber.isNotEmpty()) {
                        tokens.add(
                            TokenData(
                                tokenId = System.currentTimeMillis(),
                                token = currentNumber.toString()
                            )
                        )
                        currentNumber.clear()
                    }
                    tokens.add(TokenData(threeChar, System.currentTimeMillis()))
                    i += 3
                    continue
                }
            }

            if (i + 1 < expression.length) {
                val twoChar = expression.substring(i, i + 2)
                if (twoChar in setOf("ln", "π", "Φ")) {
                    if (currentNumber.isNotEmpty()) {
                        tokens.add(
                            TokenData(
                                currentNumber.toString(),
                                System.currentTimeMillis()
                            )
                        )
                        currentNumber.clear()
                    }
                    tokens.add(TokenData(twoChar, System.currentTimeMillis()))
                    i += 2
                    continue
                }
            }

            if (char in operators) {
                // Save the accumulated number if any
                if (currentNumber.isNotEmpty()) {
                    tokens.add(TokenData(currentNumber.toString(), System.currentTimeMillis()))
                    currentNumber.clear()
                }
                tokens.add(TokenData(char.toString(), System.currentTimeMillis()))
            } else if (char.isWhitespace()) {
                tokens.add(TokenData(" ", System.currentTimeMillis()))
            } else {
                currentNumber.append(char)
            }
            i++
        }

        if (currentNumber.isNotEmpty()) {
            tokens.add(TokenData(currentNumber.toString(), System.currentTimeMillis()))
        }

        return tokens
    }
}

data class TokenData(
    val token: String,
    val tokenId: Long,
)