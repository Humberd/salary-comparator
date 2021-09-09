package pl.humberd.salary_comparator.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.murgupluoglu.flagkit.FlagKit
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

data class DropdownItemModel(
    val name: String,
    val value: String,
    val icon: Int? = null
)

@Composable
fun Dropdown(
    label: String? = null,
    items: List<DropdownItemModel> = emptyList(),
    value: String = "",
    onValueChange: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var controlSize by remember { mutableStateOf(Size.Zero) }

    TextButton(
        onClick = {
            expanded = true
        },
        modifier = Modifier.onGloballyPositioned {
            controlSize = it.size.toSize()
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                if (label != null) {
                    Text(
                        label,
                        fontSize = 12.sp
                    )
                }
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
        onDismissRequest = { expanded = false },
        Modifier.width(with(LocalDensity.current) { controlSize.width.toDp() })
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (model.icon != null) {
                Icon(
                    painter = painterResource(id = model.icon),
                    contentDescription = "",
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
            Text(
                model.name,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDropdown() {
    var state by remember { mutableStateOf("PLN") }

    SalarycomparatorTheme {
        val context = LocalContext.current
        Dropdown(
            label = "From",
            items = CurrencyService.getCurrencies()
                .map { DropdownItemModel(it.name, it.name, null) },
            value = state,
            onValueChange = {
                println(FlagKit.getResId(context, "pl"))
                state = it
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDropdownItem() {
    SalarycomparatorTheme {
        DropdownItem(
            DropdownItemModel("Poland (PLN)", "PLN", FlagKit.getResId(LocalContext.current, "pl")),
            onClick = {}
        )
    }
}
