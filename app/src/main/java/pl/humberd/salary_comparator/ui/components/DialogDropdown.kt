package pl.humberd.salary_comparator.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pl.humberd.salary_comparator.ui.screens.Dialog
import pl.humberd.salary_comparator.ui.screens.DialogRef
import pl.humberd.salary_comparator.ui.screens.DropdownInput
import pl.humberd.salary_comparator.ui.screens.DropdownOutput

@Composable
fun DialogDropdown(
    navController: NavController,
    label: String? = null,
    items: List<DropdownItemModel> = emptyList(),
    value: String = "",
    onValueChange: (String) -> Unit = {}
) {
    TextButton(
        onClick = {
            Dialog.DROPDOWN.open(navController, DropdownInput(value)) {

            }
        },
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
                Icons.Rounded.KeyboardArrowDown,
                contentDescription = "",
                Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun DialogDropdownScreen(dialogRef: DialogRef<DropdownInput, DropdownOutput>) {
    Surface(
        Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        Column {
            Text("selectedId = ${dialogRef.input.selectedId}")
            Button(onClick = {
                dialogRef.close(DropdownOutput.SELECTED(""))

            }) {
                Text("close me")
            }
        }
    }
}

@Composable
fun DialogDropdownItem(model: DropdownItemModel, onClick: () -> Unit) {
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

//@Preview(showBackground = true)
//@Composable
//fun PreviewDialogDropdown() {
//    var state by remember { mutableStateOf("PLN") }
//
//    SalarycomparatorTheme {
//        val context = LocalContext.current
//        DialogDropdown(
//            label = "From",
//            items = CurrencyService.getCurrencies()
//                .map { DropdownItemModel(it.name, it.name, null) },
//            value = state,
//            onValueChange = {
//                println(FlagKit.getResId(context, "pl"))
//                state = it
//            }
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewDialogDropdownItem() {
//    SalarycomparatorTheme {
//        DialogDropdownItem(
//            DropdownItemModel("Poland (PLN)", "PLN", FlagKit.getResId(LocalContext.current, "pl")),
//            onClick = {}
//        )
//    }
//}
