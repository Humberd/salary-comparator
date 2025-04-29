package pl.humberd.salary_comparator.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.humberd.salary_comparator.services.AmountUnit
import pl.humberd.salary_comparator.ui.theme.SalaryConverterTheme

data class InlineRadioModel(
    val name: String,
    val value: String
)

@Composable
fun InlineRadio(
    items: Collection<InlineRadioModel>,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        Modifier.fillMaxWidth()
            .height(50.dp),
    ) {
        items.forEachIndexed { index, it ->
            InlineRadioItem(it, value, onValueChange)
        }
    }
}

@Composable
private fun RowScope.InlineRadioItem(
    model: InlineRadioModel,
    value: String,
    onValueChange: (String) -> Unit
) {
    val isSelected = value == model.value
    Button(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight(),
        onClick = { onValueChange(model.value) },
        contentPadding = PaddingValues(0.dp),
        shape = MaterialTheme.shapes.large,
        border = if (isSelected) null else ButtonDefaults.outlinedBorder,
        colors = if (isSelected) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors()
    ) {
        Text(model.name)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInlineRadio() {
    SalaryConverterTheme {
        InlineRadio(
            items = AmountUnit.values().map {
                InlineRadioModel(
                    name = it.name,
                    value = it.name
                )
            },
            value = "",
            onValueChange = {}
        )
    }
}
