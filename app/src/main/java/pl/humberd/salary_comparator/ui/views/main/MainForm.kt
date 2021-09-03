package pl.humberd.salary_comparator.ui.views.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pl.humberd.salary_comparator.ui.components.Dropdown
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun MainForm() {
    Row {
        Dropdown(label = "From")
        Dropdown(label = "To")
    }
}

@Preview(showBackground =  true)
@Composable
fun PreviewMainForm() {
    SalarycomparatorTheme {
        MainForm()
    }
}
