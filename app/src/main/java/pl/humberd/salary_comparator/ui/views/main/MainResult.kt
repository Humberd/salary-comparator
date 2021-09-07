package pl.humberd.salary_comparator.ui.views.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.humberd.salary_comparator.ui.components.AmountUnit
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme
import java.util.*

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

    if (results.isEmpty()) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "No data",
                modifier = Modifier.padding(16.dp)
                    .align(CenterHorizontally)
            )
        }

        return
    }

    Column {
        Row {
            columnLabels.forEach {
                TableCell(
                    text = it,
                    alignment = CenterHorizontally,
                    isLabel = true
                )
            }
        }
        rowLabels.forEach {
            Row {
                TableCell(
                    text = it.name.lowercase(Locale.getDefault()),
                    isLabel = true
                )
                results[it].orEmpty().forEach {
                    TableCell(
                        text = it.second.toString(),
                        alignment = CenterHorizontally,
                        isLabel = false
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    alignment: Alignment.Horizontal = Alignment.Start,
    isLabel: Boolean = false
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .height(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = alignment
    ) {
        Text(
            text = text,
            color = if (isLabel) Color.Gray else Color.Unspecified
        )
    }
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
