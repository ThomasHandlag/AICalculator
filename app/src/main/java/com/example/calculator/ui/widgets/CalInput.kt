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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.example.calculator.ui.viewmodel.BasicViewModel
import com.example.calculator.ui.viewmodel.TokenData

@Composable
fun CalInput(
    value: List<TokenData> = emptyList(),
    onPaste: (index: Long) -> Unit = {},
    textStyle: TextStyle = TextStyle(fontSize = 60.sp, color = Color.Black),
    basicViewModel: BasicViewModel
) {
    val selectedIndex = basicViewModel.selectedTokenId.collectAsState()
    val scrollState = rememberScrollState()

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
                value.forEach { token ->
                    TokenItem(
                        token = token,
                        selectedIndex = selectedIndex.value,
                        setSelectedIndex = { index -> basicViewModel.setSelectedTokenId(index) },
                        textStyle = textStyle,
                        onPaste = {
                            onPaste(token.tokenId)
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun TokenItem(
    token: TokenData,
    selectedIndex: Long?,
    setSelectedIndex: (index: Long?) -> Unit,
    onPaste: (index: Long) -> Unit,
    textStyle: TextStyle = TextStyle(fontSize = 60.sp, color = Color.Black),
) {
    val (showOption, setShowOption) = rememberSaveable { mutableStateOf(false) }
    val isSelected = selectedIndex == token.tokenId
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
                        setSelectedIndex(if (selectedIndex == token.tokenId) null else token.tokenId)
                    }
                ),
            color = if (isSelected) Color.Green.copy(alpha = .4f) else if (token.token == " ") Color.Gray.copy(
                .5f
            ) else Color.Transparent,
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = token.token,
                style = textStyle.copy(
                    letterSpacing = 0.sp,
                ),
                modifier = Modifier.padding(horizontal = 3.dp, vertical = 2.dp)
            )
        }

        if (showOption) {
            Popup(popupPositionProvider = dropdownPopupPositioner) {
                Surface(
                    onClick = {
                        onPaste(token.tokenId)
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

//fun findNTokenIndex(exp: String, token: String, id: Int): Int {
//    var tempValue = exp
//    var nIndex = -1
//    var i = 0
//    while (i < id) {
//        val cIndex = tempValue.indexOf(token) + token.length
//        if (nIndex > -1) {
//            nIndex += cIndex
//        } else {
//            nIndex = cIndex
//        }
//        i++
//        tempValue = exp.substring(nIndex)
//    }
//
//    return if (nIndex > -1) nIndex else exp.length
//}
