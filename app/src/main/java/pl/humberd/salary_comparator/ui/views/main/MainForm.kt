package pl.humberd.salary_comparator.ui.views.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import pl.humberd.salary_comparator.R
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.components.Dropdown
import pl.humberd.salary_comparator.ui.components.DropdownItemModel
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun MainForm(viewModel: MainFormViewModel = MainFormViewModel()) {
    val sourceCurrency by viewModel.sourceCurrency.observeAsState("")
    val targetCurrency by viewModel.targetCurrency.observeAsState("")
    val value by viewModel.rawValue.observeAsState("")

    Column {
        Row(
            Modifier.fillMaxWidth(),
        ) {
            Column(
                Modifier.weight(0.5f)
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
                Modifier.weight(0.5f)
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
        Row {
            TextField(
                label = { Text("Value") },
                value = value,
                onValueChange = {
                    viewModel.updateValue(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainForm() {
    SalarycomparatorTheme {
        MainForm()
    }
}
