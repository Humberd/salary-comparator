package pl.humberd.salary_comparator.ui.screens.converter_form.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.humberd.salary_comparator.services.AmountUnit
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme
import java.util.*

typealias Currency = String;

@Composable
fun MainResult(
    results: Map<AmountUnit, List<Pair<Currency, Double>>>
) {
    val rowLabels = results.keys
    val uniqueLabels = results.values
        .flatMap { it }
        .map { it.first }
        .toSet()
    val columnLabels = setOf("") + uniqueLabels

    if (results.isEmpty()) {
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
                        text = it.second.toInt().toString(),
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
    CompositionLocalProvider(LocalContentAlpha provides if (isLabel) ContentAlpha.medium else ContentAlpha.high) {
        Column(
            modifier = Modifier
                .weight(1f)
                .height(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = alignment
        ) {
            Text(
                text = text,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainResult() {
    SalarycomparatorTheme {
        MainResult(
            mapOf(
                AmountUnit.HOUR to listOf(
                    Pair("EUR", 10.0),
                    Pair("PLN", 40.0)
                ),
                AmountUnit.DAY to listOf(
                    Pair("EUR", 80.0),
                    Pair("PLN", 320.0)
                ),
                AmountUnit.MONTH to listOf(
                    Pair("EUR", 1600.0),
                    Pair("PLN", 8000.0)
                ),
                AmountUnit.YEAR to listOf(
                    Pair("EUR", 19_200.0),
                    Pair("PLN", 76_800.0)
                ),
            )
        )
    }
}
