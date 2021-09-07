package pl.humberd.salary_comparator.ui.screens.converter_form

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.humberd.salary_comparator.R
import pl.humberd.salary_comparator.ui.screens.converter_form.components.MainForm
import pl.humberd.salary_comparator.ui.screens.converter_form.components.MainResult
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun ConverterFormScreen(viewModel: ConverterFormViewModel = viewModel()) {
    val result by viewModel.result.observeAsState(emptyMap())

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            if (result.isEmpty()) {
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
                        tint = LocalContentColor.current.copy(alpha = 0.4f)
                    )
                    Text("Fill the form below")
                }
            } else {
                Column(
                    Modifier.padding(top=16.dp, start = 8.dp, end = 8.dp)
                ) {
                    MainResult(result)
                }
            }
        }

        Column {
            Divider(Modifier.padding(bottom = 8.dp))
            Column(Modifier.padding(horizontal = 8.dp)) {
                MainForm(viewModel)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewConverterFormScreen() {
    SalarycomparatorTheme {
        ConverterFormScreen(ConverterFormViewModel())
    }
}
