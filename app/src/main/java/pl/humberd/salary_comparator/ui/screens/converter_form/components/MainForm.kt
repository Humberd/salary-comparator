package pl.humberd.salary_comparator.ui.screens.converter_form.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.humberd.salary_comparator.R
import pl.humberd.salary_comparator.services.CURRENCIES
import pl.humberd.salary_comparator.ui.components.AmountUnit
import pl.humberd.salary_comparator.ui.components.DialogDropdown
import pl.humberd.salary_comparator.ui.components.Dropdown
import pl.humberd.salary_comparator.ui.components.DropdownItemModel
import pl.humberd.salary_comparator.ui.screens.converter_form.ConverterFormViewModel
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun MainForm(viewModel: ConverterFormViewModel = viewModel(), navController: NavController) {
    val sourceCurrency by viewModel.sourceCurrency.observeAsState("")
    val targetCurrency by viewModel.targetCurrency.observeAsState("")
    val value by viewModel.amount.observeAsState("")
    val unit by viewModel.unit.observeAsState(AmountUnit.MONTH)
    val mostPopularCurrencies = remember { setOf("chf", "eur", "gbp", "usd", "pl") }
    val focusManager = LocalFocusManager.current

    Column {
        Row(
            Modifier.fillMaxWidth(),
        ) {
            Column(
                Modifier.weight(0.5f),
            ) {
                DialogDropdown(
                    navController = navController,
                    label = stringResource(R.string.source_currency_label),
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
                                icon = it.getFlagId(LocalContext.current)
                            )
                        }
                        .sortedBy { it.name },
                    value = sourceCurrency,
                    onValueChange = {
                        viewModel.updateSourceCurrency(it)
                    },
                    valueDisplayTransformer = { it.uppercase(Locale.getDefault()) }
                )
            }
            IconButton(
                onClick = {
                    viewModel.swap()
                },
                Modifier.weight(0.2f),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_swap_horiz_24),
                    contentDescription = stringResource(R.string.form_swap_button_aria_label)
                )
            }
            Column(
                Modifier.weight(0.5f),
            ) {
                DialogDropdown(
                    navController = navController,
                    label = stringResource(R.string.target_currency_label),
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
                                icon = it.getFlagId(LocalContext.current)
                            )
                        }
                        .sortedBy { it.name },
                    value = targetCurrency,
                    onValueChange = {
                        viewModel.updateTargetCurrency(it)
                    },
                    valueDisplayTransformer = { it.uppercase(Locale.getDefault()) }
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                modifier = Modifier.weight(0.5f),
                placeholder = { Text("Amount") },
                value = value,
                onValueChange = {
                    viewModel.updateValue(it)
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

            Column(
                modifier = Modifier.weight(0.5f),
            ) {
                Dropdown(
                    items = AmountUnit.values().map {
                        DropdownItemModel(it.name, it.name, icon = null)
                    },
                    value = unit.toString(),
                    onValueChange = {
                        viewModel.updateUnit(it)
                    }
                )
            }
        }

        Button(
            onClick = {
                viewModel.convert()
                focusManager.clearFocus()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            ),
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(bottom = 16.dp, top = 16.dp)
        ) {
            Text("Convert")
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
