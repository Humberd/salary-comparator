package pl.humberd.salary_comparator.ui.views.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.humberd.salary_comparator.ui.components.AmountUnit
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

typealias Currency = String;

@Composable
fun MainResult(
    results: Map<AmountUnit, List<Pair<Currency, Int>>>
) {
    val rowLabels = results.keys
    val uniqueLabels = results.values
        .flatMap { it }
        .map { it.first }
        .toSet()
    val columnLabels = setOf("") + uniqueLabels

    Column {
        Row {
            columnLabels.forEach {
                TableCell(
                    text = it,
                    textAlign = TextAlign.Center,
                    isLabel = true
                )
            }
        }
        rowLabels.forEach {
            Row {

            }
        }
    }
}

@Composable
fun TableCell(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    isLabel: Boolean = false
) {
    Text(
        text = text,
        modifier = Modifier
            .width(50.dp)
            .border(0.5.dp, Color.Black),
        textAlign = textAlign,
        color = if (isLabel) Color.Gray else Color.Unspecified
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainResult() {
    SalarycomparatorTheme {
        MainResult(
            mapOf(
                AmountUnit.HOUR to listOf(
                    Pair("EUR", 10),
                    Pair("PLN", 40)
                ),
                AmountUnit.DAY to listOf(
                    Pair("EUR", 80),
                    Pair("PLN", 320)
                ),
                AmountUnit.MONTH to listOf(
                    Pair("EUR", 1600),
                    Pair("PLN", 8000)
                ),
                AmountUnit.YEAR to listOf(
                    Pair("EUR", 19_200),
                    Pair("PLN", 76_800)
                ),
            )
        )
    }
}
