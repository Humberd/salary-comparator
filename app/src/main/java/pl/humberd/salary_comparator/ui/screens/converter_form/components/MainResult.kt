package pl.humberd.salary_comparator.ui.screens.converter_form.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.humberd.salary_comparator.services.AmountUnit
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme
import java.text.NumberFormat
import java.util.*

typealias Currency = String;

@Composable
fun MainResult(
    results: Map<AmountUnit, List<Pair<Currency, Double>>>
) {
    val rowLabels = results.keys
    val uniqueLabels = results.values.firstOrNull()
        ?.map { it.first }
    val columnLabels = listOf("") + (uniqueLabels?: emptyList())
    val context = LocalContext.current

    val formatter = remember { NumberFormat.getInstance() }

    if (results.isEmpty()) {
        return
    }

    Column {
        // Top row
        Row {
            columnLabels.forEach {
                TableCell(
                    icon = CurrencyService.get(it)?.getFlagId(context),
                    text = it.uppercase(Locale.getDefault()),
                    alignment = End,
                    isLabel = true
                )
            }
        }

        // Other rows
        rowLabels.forEach {
            Row {
                TableCell(
                    text = it.getName(),
                    isLabel = true
                )
                results[it].orEmpty().forEach {
                    TableCell(
                        text = formatter.format(it.second.toInt()),
                        alignment = End,
                        isLabel = false
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    @DrawableRes icon: Int? = null,
    text: String,
    alignment: Arrangement.Horizontal = Arrangement.Start,
    isLabel: Boolean = false
) {
    CompositionLocalProvider(LocalContentAlpha provides if (isLabel) ContentAlpha.medium else ContentAlpha.high) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = alignment
        ) {
            if (icon != null) {
                Icon(
                    painterResource(icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Text(
                text = text,
                fontSize = 20.sp
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
                    Pair("eur", 10.0),
                    Pair("pln", 40.0)
                ),
                AmountUnit.DAY to listOf(
                    Pair("eur", 80.0),
                    Pair("pln", 320.0)
                ),
                AmountUnit.MONTH to listOf(
                    Pair("eur", 1600.0),
                    Pair("pln", 8000.0)
                ),
                AmountUnit.YEAR to listOf(
                    Pair("eur", 19_200.0),
                    Pair("pln", 76_800.0)
                ),
            )
        )
    }
}
