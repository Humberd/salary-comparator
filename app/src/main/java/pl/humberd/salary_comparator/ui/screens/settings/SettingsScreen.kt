package pl.humberd.salary_comparator.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.humberd.salary_comparator.R
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.screens.settings.components.SettingCell
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val lastUpdate by remember { CurrencyService.lastUpdate }
    val isLoading by remember { viewModel.isLoading }
    val context = LocalContext.current

    Column {
        SettingCell(
            primaryText = "Currency Exchange Rate",
            secondaryText = "Last update ${lastUpdate}",
        ) {
            TextButton(
                enabled = !isLoading,
                onClick = { viewModel.updateExchangeRate(context, scaffoldState) }
            ) {
                if (!isLoading) {
                    Text("Update")
                } else {
                    Icon(painterResource(R.drawable.ic_baseline_sync_24), contentDescription = null)
                }
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
