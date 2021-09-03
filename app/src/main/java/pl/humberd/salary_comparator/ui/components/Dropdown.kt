package pl.humberd.salary_comparator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme
import java.util.*

data class DropdownItemModel(
    val text: String,
    val value: String,
    val icon: ImageVector? = null
)

@Composable
fun Dropdown(
    label: String,
    items: List<DropdownItemModel> = emptyList(),
    onValueChange: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var filteredItems by remember { mutableStateOf(items) }

    fun filterItems() {
        val searchTextLc = searchText.lowercase(Locale.getDefault())
        filteredItems =
            items.filter { it.text.lowercase(Locale.getDefault()).contains(searchTextLc) }
    }

    TextField(
        label = { Text(label) },
        value = searchText,
        trailingIcon = {
            Icon(
                if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                contentDescription = ""
            )
        },
        onValueChange = {
            searchText = it
            filterItems()
        },
        singleLine = true,
        modifier = Modifier
            .onFocusChanged {
                if (it.isFocused) {
                    expanded = true
                }
            }
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        if (filteredItems.size == 0) {
            Text(text = "Not found")
        }
        filteredItems.forEach {
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
                    modifier = Modifier.padding(end = 16.dp)
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
    SalarycomparatorTheme {
        Dropdown(
            label = "From",
            items = listOf(
                DropdownItemModel("PLN", "PLN", Icons.Default.ThumbUp),
                DropdownItemModel("EUR", "EUR", Icons.Default.ThumbUp),
                DropdownItemModel("USD", "USD", Icons.Default.ThumbUp)
            )
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
