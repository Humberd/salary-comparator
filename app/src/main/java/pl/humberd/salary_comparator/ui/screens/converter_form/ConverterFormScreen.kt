package pl.humberd.salary_comparator.ui.screens.converter_form

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.humberd.salary_comparator.R
import pl.humberd.salary_comparator.ui.screens.Screen
import pl.humberd.salary_comparator.ui.screens.converter_form.components.MainForm
import pl.humberd.salary_comparator.ui.screens.converter_form.components.MainResult
import pl.humberd.salary_comparator.ui.theme.SalaryConverterTheme

@ExperimentalComposeUiApi
@Composable
fun ConverterFormScreen(
    viewModel: ConverterFormViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {
    val result by viewModel.result.observeAsState(emptyMap())
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = {
                Text(Screen.CONVERTER_FORM.getName())
            }
        )

        Column(
            Modifier.weight(1f)
        ) {
            if (result.isEmpty()) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(top = 0.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_baseline_table_view_24),
                            contentDescription = "",
                            modifier = Modifier.size(120.dp)
                        )
                        Text(stringResource(R.string.screens_converter_no_results))
                    }
                }
            } else {
                Column(
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    MainResult(result, stringResource(R.string.screens_converter_rate_per))
                }
            }
        }

        Column {
            Divider(Modifier.padding(bottom = 16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                MainForm(viewModel, navController)
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun PreviewConverterFormScreen() {
    SalaryConverterTheme {
        ConverterFormScreen(ConverterFormViewModel())
    }
}
