package pl.humberd.salary_comparator.ui.views.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.murgupluoglu.flagkit.FlagKit
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.components.Dropdown
import pl.humberd.salary_comparator.ui.components.DropdownItemModel
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun MainForm() {
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
                value = "PLN"
            )
        }
        Column(
            Modifier.weight(0.5f)
        ) {
            Dropdown(
                label = "To",
                items = CurrencyService.getAvailableCurrencies(LocalContext.current)
                    .map { DropdownItemModel(it.name, it.icon) },
                value = "EUR"
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
