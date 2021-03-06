package pl.humberd.salary_comparator.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.humberd.salary_comparator.R
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.screens.Screen
import pl.humberd.salary_comparator.ui.screens.settings.components.SettingCell
import pl.humberd.salary_comparator.ui.theme.SalaryConverterTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController = rememberNavController()
) {
    val lastUpdate by remember { CurrencyService.lastUpdate }
    val isExchangeRateUpdateInProgress by remember { viewModel.isExchangeRateUpdateInProgress }
    val context = LocalContext.current

    Column {
        TopAppBar(
            title = {
                Text(Screen.SETTINGS.getName())
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.generic_back_button_aria_label),
                    )
                }
            }
        )

        SettingCell(
            primaryText = stringResource(R.string.screens_settings_options_exchange_rate_name),
            secondaryText = stringResource(
                R.string.screens_settings_options_exchange_rate_description,
                lastUpdate ?: ""
            ),
        ) {
            TextButton(
                enabled = !isExchangeRateUpdateInProgress,
                onClick = { viewModel.updateExchangeRate(context, scaffoldState) }
            ) {
                if (!isExchangeRateUpdateInProgress) {
                    Text(stringResource(R.string.screens_settings_options_exchange_rate_action_name))
                } else {
                    CircularProgressIndicator(
                        Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SalaryConverterTheme {
        SettingsScreen(SettingsViewModel())
    }
}
