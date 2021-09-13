package pl.humberd.salary_comparator.ui.screens.converter_form.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pl.humberd.salary_comparator.R
import pl.humberd.salary_comparator.services.AmountUnit
import pl.humberd.salary_comparator.services.CURRENCIES
import pl.humberd.salary_comparator.store.ConverterFormStateSerializer
import pl.humberd.salary_comparator.store.converterFormStateDataStore
import pl.humberd.salary_comparator.ui.components.DialogDropdown
import pl.humberd.salary_comparator.ui.components.DropdownItemModel
import pl.humberd.salary_comparator.ui.components.InlineRadio
import pl.humberd.salary_comparator.ui.components.InlineRadioModel
import pl.humberd.salary_comparator.ui.screens.converter_form.ConverterFormViewModel
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun MainForm(viewModel: ConverterFormViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    val state by context.converterFormStateDataStore.data.collectAsState(
        ConverterFormStateSerializer.defaultValue
    )

    LaunchedEffect(state) {
        viewModel.convert(state)
    }

    val scope = rememberCoroutineScope()

    val mostPopularCurrencies = remember { setOf("chf", "eur", "gbp", "usd", "pl") }
    val focusManager = LocalFocusManager.current

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.width(150.dp),
            ) {
                DialogDropdown(
                    navController = navController,
                    items = CURRENCIES
                        .map {
                            DropdownItemModel(
                                "${it.getName()} (${
                                    it.id.uppercase(
                                        Locale.getDefault()
                                    )
                                })",
                                it.id,
                                mostPopular = mostPopularCurrencies.contains(it.id),
                                icon = it.getFlagId(context)
                            )
                        }
                        .sortedBy { it.name },
                    value = state.sourceCurrency,
                    onValueChange = { newValue ->
                        scope.launch {
                            context.converterFormStateDataStore.updateData {
                                it.toBuilder().apply {
                                    sourceCurrency = newValue
                                }.build()
                            }
                        }
                    },
                    valueDisplayTransformer = { it.uppercase(Locale.getDefault()) }
                )
            }
            IconButton(
                onClick = { viewModel.swap(context) },
                Modifier.width(64.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_swap_horiz_24),
                    contentDescription = stringResource(R.string.form_swap_button_aria_label)
                )
            }
            Column(
                Modifier.width(150.dp),
            ) {
                DialogDropdown(
                    navController = navController,
                    items = CURRENCIES
                        .map {
                            DropdownItemModel(
                                "${it.getName()} (${
                                    it.id.uppercase(
                                        Locale.getDefault()
                                    )
                                })",
                                it.id,
                                mostPopular = mostPopularCurrencies.contains(it.id),
                                icon = it.getFlagId(context)
                            )
                        }
                        .sortedBy { it.name },
                    value = state.targetCurrency,
                    onValueChange = { newValue ->
                        scope.launch {
                            context.converterFormStateDataStore.updateData {
                                it.toBuilder().apply {
                                    targetCurrency = newValue
                                }.build()
                            }
                        }
                    },
                    valueDisplayTransformer = { it.uppercase(Locale.getDefault()) }
                )
            }
        }
        Row(
            Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                modifier = Modifier.width(150.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.screens_converter_amount_field_placeholder),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center
                ),
                value = state.amount,
                onValueChange = { newValue ->
                    scope.launch {
                        context.converterFormStateDataStore.updateData {
                            it.toBuilder().apply {
                                amount = newValue
                            }.build()
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
            )

//            Column(
//                modifier = Modifier.weight(0.5f),
//            ) {
//                Dropdown(
//                    items = AmountUnit.values().map {
//                        DropdownItemModel(it.getName(), it.name, icon = null)
//                    },
//                    value = unit.toString(),
//                    onValueChange = {
//                        viewModel.updateUnit(it)
//                    },
//                )
//            }
        }

        Row(
            Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(stringResource(R.string.main_form_per_label))
            }
        }

        Row(
            Modifier.padding(bottom = 16.dp)
        ) {
            InlineRadio(
                items = AmountUnit.values().map {
                    InlineRadioModel(
                        name = it.getName(),
                        value = it.name
                    )
                },
                value = state.amountUnit,
                onValueChange = { newValue ->
                    scope.launch {
                        context.converterFormStateDataStore.updateData {
                            it.toBuilder().apply {
                                amountUnit = newValue
                            }.build()
                        }
                    }
                },
            )
        }
    }
}


@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun PreviewMainForm() {
    SalarycomparatorTheme {
        MainForm(ConverterFormViewModel(), NavController(LocalContext.current))
    }
}
