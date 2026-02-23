package com.example.calculator.ui.widgets

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.example.calculator.ui.viewModel.BasicViewModel
import kotlin.text.indexOf

data class CalToken(
    val id: Int,
    val value: String,
    val isOperator: Boolean
)

fun parseExpression(expression: String): List<CalToken> {
    if (expression.isEmpty()) return emptyList()

    val tokens = mutableListOf<CalToken>()
    val operators = setOf('+', '-', 'x', '×', '÷', '/', '%', '(', ')', '^', '√', '∛')
    val currentNumber = StringBuilder()

    var i = 0
    var opCount = 0
    var oprCount = 0
    while (i < expression.length) {
        val char = expression[i]

        if (i + 2 < expression.length) {
            val threeChar = expression.substring(i, i + 3)
            if (threeChar in setOf("sin", "cos", "tan", "log")) {
                if (currentNumber.isNotEmpty()) {
                    tokens.add(CalToken(oprCount++, currentNumber.toString(), isOperator = false))
                    currentNumber.clear()
                }
                tokens.add(CalToken(opCount++, threeChar, isOperator = true))
                i += 3
                continue
            }
        }

        if (i + 1 < expression.length) {
            val twoChar = expression.substring(i, i + 2)
            if (twoChar in setOf("ln", "π", "Φ")) {
                if (currentNumber.isNotEmpty()) {
                    tokens.add(CalToken(oprCount++, currentNumber.toString(), isOperator = false))
                    currentNumber.clear()
                }
                tokens.add(CalToken(opCount++, twoChar, isOperator = true))
                i += 2
                continue
            }
        }

        if (char in operators) {
            // Save the accumulated number if any
            if (currentNumber.isNotEmpty()) {
                tokens.add(CalToken(oprCount++, currentNumber.toString(), isOperator = false))
                currentNumber.clear()
            }
            tokens.add(CalToken(opCount++, char.toString(), isOperator = true))
        } else if (char.isWhitespace()) {
            // Skip whitespace
        } else {
            currentNumber.append(char)
        }
        i++
    }

    if (currentNumber.isNotEmpty()) {
        tokens.add(CalToken(oprCount, currentNumber.toString(), isOperator = false))
    }

    return tokens
}

@Composable
fun CalInput(
    value: String = "23+45×6",
    setIndex: (index: Int) -> Unit = { _ -> },
    onPaste: (index: Int) -> Unit = {},
    textStyle: TextStyle = TextStyle(fontSize = 60.sp, color = Color.Black),
    basicViewModel: BasicViewModel
) {
    val selectedIndex by basicViewModel.tokenIndex.collectAsState()
    val scrollState = rememberScrollState()
    val tokens = parseExpression(value)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(2.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (value.isEmpty()) {
            Text(
                text = "0",
                style = textStyle.copy(color = Color.Gray.copy(alpha = 0.5f)),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        } else {
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tokens.forEachIndexed { index, token ->
                    val isSelected = selectedIndex == index
                    TokenItem(
                        token = token,
                        isSelected = isSelected,
                        index = index,
                        selectedIndex = selectedIndex,
                        setSelectedIndex = { index -> basicViewModel.setTokenIndex(index) },
                        setIndex = setIndex,
                        value = value,
                        textStyle = textStyle,
                        onPaste = onPaste
                    )
                }
            }
        }
    }
}

@Composable
fun TokenItem(
    token: CalToken,
    isSelected: Boolean,
    index: Int,
    selectedIndex: Int?,
    setSelectedIndex: (index: Int?) -> Unit,
    setIndex: (index: Int) -> Unit,
    onPaste: (index: Int) -> Unit,
    value: String,
    textStyle: TextStyle = TextStyle(fontSize = 60.sp, color = Color.Black),
) {
    val (showOption, setShowOption) = rememberSaveable { mutableStateOf(false) }
    val dropdownPopupPositioner = remember {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset {
                // Position the popup below the anchor aligned horizontally with the anchor's
                // center.
                return IntOffset(
                    x = anchorBounds.left,
                    y = anchorBounds.top - 10,
                )
            }
        }
    }
    Box {
        Surface(
            modifier = Modifier
                .padding(horizontal = 0.dp)
                .combinedClickable(
                    true, onLongClick = {
                        setShowOption(true)
                    },
                    onClick = {
                        setSelectedIndex(if (selectedIndex == index) null else index)
                        if (selectedIndex != null) {
                            setIndex(findNTokenIndex(
                                value,
                                token.value,
                                token.id
                            ))
                        } else {
                            setIndex(value.length)
                        }
                    }
                ),
            color = if (isSelected) Color.Green.copy(alpha = .4f) else Color.Transparent,
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = token.value,
                style = textStyle.copy(
                    letterSpacing = 0.sp,
                    fontWeight = if (token.isOperator) FontWeight.Bold else FontWeight.Normal,
                ),
                modifier = Modifier.padding(horizontal = 3.dp, vertical = 2.dp)
            )
        }

        if (showOption) {
            Popup(popupPositionProvider = dropdownPopupPositioner) {
                Surface(
                    onClick = {
                        onPaste(index)
                        setShowOption(false)
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                ) {
                    Text(
                        "Paste",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

fun findNTokenIndex(exp: String, token: String, id: Int): Int {
    var tempValue = exp
    var nIndex = -1
    var i = 0
    while (i < id) {
        val cIndex = tempValue.indexOf(token) + token.length
        if (nIndex > -1) {
            nIndex+=cIndex
        }
        else {
            nIndex = cIndex
        }
        i++
        tempValue = exp.substring(nIndex)
    }

    return if (nIndex > -1) nIndex else exp.length
}
