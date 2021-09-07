package pl.humberd.salary_comparator.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.humberd.salary_comparator.ui.screens.settings.components.SettingCell
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()){
    Column {
        SettingCell(
            primaryText = "Currency Exchange Rate",
            secondaryText = "Last update 3 months ago",
        ) {
            TextButton(onClick = { /*TODO*/ }) {
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
