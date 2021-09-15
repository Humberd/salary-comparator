package pl.humberd.salary_comparator.ui.screens.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.humberd.salary_comparator.ui.theme.SalaryConverterTheme

@Composable
fun SettingCell(
    primaryText: String,
    secondaryText: String,
    action: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = primaryText,
                fontSize = 15.sp
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    secondaryText,
                    fontSize = 14.sp
                )
            }
        }
        Column {
            action()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSettingCell() {
    SalaryConverterTheme {
        Column {
            repeat(3) {
                SettingCell(
                    primaryText = "Currency Exchange Rate",
                    secondaryText = "Last update 3 months ago",
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text("Update")
                    }
                }
            }
        }
    }
}
