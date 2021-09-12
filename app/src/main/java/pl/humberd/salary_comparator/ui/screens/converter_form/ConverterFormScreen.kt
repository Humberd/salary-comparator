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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.humberd.salary_comparator.R
import pl.humberd.salary_comparator.ui.screens.Screen
import pl.humberd.salary_comparator.ui.screens.converter_form.components.MainForm
import pl.humberd.salary_comparator.ui.screens.converter_form.components.MainResult
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@ExperimentalComposeUiApi
@Composable
fun ConverterFormScreen(
    viewModel: ConverterFormViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {
    val result by viewModel.result.observeAsState(emptyMap())

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = {
                Text(Screen.CONVERTER_FORM.getName())
            }
        )

        Column {
            if (result.isEmpty()) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 64.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_baseline_table_view_24),
                            contentDescription = "",
                            modifier = Modifier.size(120.dp),
                        )
                        Text("Fill the form below")
                    }
                }
            } else {
                Column(
                    Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)
                ) {
                    MainResult(result)
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
    SalarycomparatorTheme {
        ConverterFormScreen(ConverterFormViewModel())
    }
}
