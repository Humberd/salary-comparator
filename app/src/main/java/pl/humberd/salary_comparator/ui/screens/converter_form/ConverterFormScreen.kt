package pl.humberd.salary_comparator.ui.views.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.humberd.salary_comparator.ui.screens.converter_form.ConverterFormViewModel
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun ConverterFormScreen(viewModel: ConverterFormViewModel = viewModel()) {
    val result by viewModel.result.observeAsState(emptyMap())

    Column {
        MainForm(viewModel)
        MainResult(result)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConverterFormScreen() {
    SalarycomparatorTheme {
        ConverterFormScreen()
    }
}
