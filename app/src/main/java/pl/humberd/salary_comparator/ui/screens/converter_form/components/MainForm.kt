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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.humberd.salary_comparator.R
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.components.AmountUnit
import pl.humberd.salary_comparator.ui.components.Dropdown
import pl.humberd.salary_comparator.ui.components.DropdownItemModel
import pl.humberd.salary_comparator.ui.screens.converter_form.ConverterFormViewModel
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun MainForm(viewModel: ConverterFormViewModel = viewModel()) {
    val sourceCurrency by viewModel.sourceCurrency.observeAsState("")
    val targetCurrency by viewModel.targetCurrency.observeAsState("")
    val value by viewModel.amount.observeAsState("")
    val unit by viewModel.unit.observeAsState(AmountUnit.MONTH)

    Column {
        Row(
            Modifier.fillMaxWidth(),
        ) {
            Column(
                Modifier.weight(0.5f),
            ) {
                Dropdown(
                    label = "From",
                    items = CurrencyService.getAvailableCurrencies(LocalContext.current)
                        .map { DropdownItemModel(it.name, it.icon) },
                    value = sourceCurrency,
                    onValueChange = {
                        viewModel.updateSourceCurrency(it)
                    }
                )
            }
            IconButton(
                onClick = {
                    viewModel.swap()
                },
                Modifier.weight(0.2f),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_swap_horiz_24),
                    contentDescription = ""
                )
            }
            Column(
                Modifier.weight(0.5f),
            ) {
                Dropdown(
                    label = "To",
                    items = CurrencyService.getAvailableCurrencies(LocalContext.current)
                        .map { DropdownItemModel(it.name, it.icon) },
                    value = targetCurrency,
                    onValueChange = {
                        viewModel.updateTargetCurrency(it)
                    }
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val focusManager = LocalFocusManager.current

            OutlinedTextField(
                modifier = Modifier.weight(0.5f),
                label = { Text("Amount") },
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
                )
            )

            Column(
                modifier = Modifier.weight(0.5f),
            ) {
                Dropdown(
                    items = AmountUnit.values().map {
                        DropdownItemModel(it.name, null)
                    },
                    value = unit.toString(),
                    onValueChange = {
                        viewModel.updateUnit(it)
                    }
                )
            }
        }

        OutlinedButton(
            onClick = { viewModel.convert() },
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(bottom = 16.dp, top = 16.dp)
        ) {
            Text("Convert")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainForm() {
    SalarycomparatorTheme {
        MainForm(ConverterFormViewModel())
    }
}
