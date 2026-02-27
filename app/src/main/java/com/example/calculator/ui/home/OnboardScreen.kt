package com.example.calculator.ui.home

import androidx.appcompat.app.AppCompatDelegate
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.R
import com.example.calculator.enums.AppLanguage
import com.example.calculator.navigation.Route
import com.example.calculator.ui.AppViewModelProvider
import com.example.calculator.ui.widgets.IconRadioButton
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardScreen(
    appViewModel: AppViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateTo: (path: String) -> Unit = {}
) {
    val supportLanguage: List<LangDataItem> = listOf(
        LangDataItem(
            rId = R.drawable.us,
            country = stringResource(R.string.en),
            code = AppLanguage.ENGLISH
        ),
        LangDataItem(
            rId = R.drawable.india, country = stringResource(R.string.hi),
            code = AppLanguage.HINDI
        ),
        LangDataItem(
            rId = R.drawable.fr, country = stringResource(R.string.fr),
            code = AppLanguage.FRENCH
        ),
        LangDataItem(
            rId = R.drawable.ae, country = stringResource(R.string.ar),
            code = AppLanguage.ARABIC
        ),
        LangDataItem(
            rId = R.drawable.es, country = stringResource(R.string.es),
            code = AppLanguage.SPANISH
        ),
        LangDataItem(
            rId = R.drawable.bd, country = stringResource(R.string.bn),
            code = AppLanguage.BENGALI
        ),
        LangDataItem(
            rId = R.drawable.it, country = stringResource(R.string.it),
            code = AppLanguage.ITALIAN
        ),
        LangDataItem(
            rId = R.drawable.ru, country = stringResource(R.string.ru),
            code = AppLanguage.RUSSIAN
        ),
        LangDataItem(
            rId = R.drawable.pt, country = stringResource(R.string.pt),
            code = AppLanguage.PORTUGUESE
        ),
    )

    val selectedOption by appViewModel.language.collectAsState()

    fun done() {
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
                    val isSelected: Boolean = lang.code.code == selectedOption.code
                    item {
                        LangItem(
                            isSelected = isSelected,
                            value = lang,
                            onOptionSelected = {
                                appViewModel.setLanguage(lang.code.code)
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.create(
                                        Locale.forLanguageTag(lang.code.code)
                                    )
                                )
                            }
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
    onOptionSelected: (value: AppLanguage) -> Unit
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

data class LangDataItem(val rId: Int, val country: String, val code: AppLanguage)

@Preview
@Composable
fun OnboardPreview() {
    OnboardScreen()
}