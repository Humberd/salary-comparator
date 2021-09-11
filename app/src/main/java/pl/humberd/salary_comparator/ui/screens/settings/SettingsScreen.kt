package pl.humberd.salary_comparator.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.screens.settings.components.SettingCell
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    val lastUpdate by remember { CurrencyService.lastUpdate }
    val context = LocalContext.current

    Column {
        SettingCell(
            primaryText = "Currency Exchange Rate",
            secondaryText = "Last update ${lastUpdate}",
        ) {
            TextButton(onClick = { viewModel.updateExchangeRate(context) }) {
                Text("Update")
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSettingsScreen() {
    SalarycomparatorTheme {
        SettingsScreen(SettingsViewModel())
    }
}
