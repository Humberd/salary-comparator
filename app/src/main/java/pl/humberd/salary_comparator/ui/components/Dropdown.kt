package pl.humberd.salary_comparator.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

data class DropdownItemModel(
    val text: String,
    val value: String,
    val icon: ImageVector? = null
)

@Composable
fun Dropdown(
    label: String,
    items: List<DropdownItemModel> = emptyList(),
    value: String = "",
    onValueChange: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    TextButton(
        onClick = {
            expanded = true
        },
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 0.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    label,
                    fontSize = 12.sp
                )
                Text(value)
            }
            Icon(
                if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                contentDescription = "",
                Modifier.padding(start = 8.dp)
            )
        }
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        if (items.size == 0) {
            Text(text = "Not found")
        }
        items.forEach {
            DropdownItem(
                model = it,
                onClick = {
                    expanded = false
                    onValueChange(it.value)
                }
            )
        }
    }
}

@Composable
fun DropdownItem(model: DropdownItemModel, onClick: () -> Unit) {
    DropdownMenuItem(
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (model.icon != null) {
                Icon(
                    imageVector = model.icon,
                    contentDescription = "",
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
            Text(
                model.text,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDropdown() {
    var state by remember { mutableStateOf("PLN") }

    SalarycomparatorTheme {
        Dropdown(
            label = "From",
            items = listOf(
                DropdownItemModel("PLN", "PLN", Icons.Default.ThumbUp),
                DropdownItemModel("EUR", "EUR", Icons.Default.ThumbUp),
                DropdownItemModel("USD", "USD", Icons.Default.ThumbUp)
            ),
            value = state,
            onValueChange = { state = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDropdownItem() {
    SalarycomparatorTheme {
        DropdownItem(
            DropdownItemModel("PLN", "PLN", Icons.Default.ThumbUp),
            onClick = {}
        )
    }
}
