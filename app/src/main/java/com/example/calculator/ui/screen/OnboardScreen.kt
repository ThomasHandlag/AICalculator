package com.example.calculator.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.R
import com.example.calculator.enums.AppLanguage
import com.example.calculator.navigation.Route
import com.example.calculator.ui.AppViewModelProvider
import com.example.calculator.ui.viewModel.AppViewModel
import com.example.calculator.ui.widgets.IconRadioButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardScreen(
    appViewModel: AppViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateTo: (path: String) -> Unit = {}
) {
    val supportLanguage: List<LangDataItem> = listOf(
        LangDataItem(
            rId = R.drawable.us,
            country = AppLanguage.ENGLISH.name,
            code = AppLanguage.ENGLISH.code
        ),
        LangDataItem(
            rId = R.drawable.india, country = AppLanguage.HINDI.name,
            code = AppLanguage.HINDI.code
        ),
        LangDataItem(
            rId = R.drawable.fr, country = AppLanguage.FRENCH.name,
            code = AppLanguage.FRENCH.code
        ),
        LangDataItem(
            rId = R.drawable.ae, country = AppLanguage.ARABIC.name,
            code = AppLanguage.ARABIC.code
        ),
        LangDataItem(
            rId = R.drawable.es, country = AppLanguage.SPANISH.name,
            code = AppLanguage.SPANISH.code
        ),
        LangDataItem(
            rId = R.drawable.bd, country = AppLanguage.BENGALI.name,
            code = AppLanguage.BENGALI.code
        ),
        LangDataItem(
            rId = R.drawable.it, country = AppLanguage.ITALIAN.name,
            code = AppLanguage.ITALIAN.code
        ),
        LangDataItem(
            rId = R.drawable.ru, country = AppLanguage.RUSSIAN.name,
            code = AppLanguage.RUSSIAN.code
        ),
        LangDataItem(
            rId = R.drawable.pt, country = AppLanguage.PORTUGUESE.name,
            code = AppLanguage.PORTUGUESE.code
        ),
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(appViewModel.language.value.code) }

    fun done() {
        appViewModel.setLanguage(selectedOption)
        appViewModel.setFirstLaunch()
        navigateTo(Route.Home.path)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Language") },
                actions = {
                    IconButton(
                        onClick = { done() }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Done,
                            contentDescription = "Done",
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .selectableGroup()
                    .padding(10.dp)
            ) {
                supportLanguage.forEach { lang ->
                    val isSelected: Boolean = lang.code == selectedOption
                    items(1) {
                        LangItem(
                            isSelected = isSelected,
                            value = lang,
                            onOptionSelected = onOptionSelected
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            color = Color.Gray.copy(.4f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LangItem(
    value: LangDataItem,
    isSelected: Boolean,
    onOptionSelected: (value: String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(
                56.dp
            )
            .selectable(
                selected = isSelected,
                onClick = { onOptionSelected(value.code) },
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(value.rId),
            modifier = Modifier.size(30.dp, height = 25.dp),
            contentDescription = "Flag Icon"
        )
        Text(
            text = value.country,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconRadioButton(
            selected = isSelected,
            onClick = null // null recommended for accessibility with screen readers
        )
    }
}

data class LangDataItem(val rId: Int, val country: String, val code: String)

@Preview
@Composable
fun OnboardPreview() {
    OnboardScreen()
}