package pl.humberd.salary_comparator.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.humberd.salary_comparator.ui.screens.Dialog
import pl.humberd.salary_comparator.ui.screens.DialogRef
import pl.humberd.salary_comparator.ui.screens.DropdownOutput
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme
import java.util.*

@ExperimentalComposeUiApi
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
            Dialog.DROPDOWN.open(
                navController,
                onClose = {
                    when (it) {
                        is DropdownOutput.SELECTED -> onValueChange(it.id)
                    }
                }
            ) {
                DialogDropdownScreen(it, value, items)
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

@ExperimentalComposeUiApi
@Composable
fun DialogDropdownScreen(
    dialogRef: DialogRef<DropdownOutput>,
    value: String,
    items: List<DropdownItemModel>
) {

    val scope = rememberCoroutineScope()
    var searchValue by remember { mutableStateOf(TextFieldValue("", TextRange(0))) }
    val filteredItems = remember { items.toMutableStateList() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    fun filterItems() {
        val searchLc = searchValue.text.lowercase(Locale.getDefault())
        scope.launch {
            filteredItems.clear()
            val removeAll = filteredItems.addAll(
                items.filter { it.name.lowercase(Locale.getDefault()).contains(searchLc) }
            )
            println(removeAll)
        }
    }

    DisposableEffect(Unit) {
        filterItems()

        var isDisposed = false
        scope.launch {
            delay(10)
            if (isDisposed) return@launch
            focusRequester.requestFocus()
            keyboardController?.show()
        }
        onDispose {
            isDisposed = true
        }
    }

    Surface(Modifier.fillMaxSize()) {
        Column {
            Column {
                TextField(
                    value = searchValue,
                    onValueChange = {
                        searchValue = it
                        filterItems()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    placeholder = { Text("Search...") },
                    leadingIcon = {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "",
                            Modifier.clickable { dialogRef.close(DropdownOutput.CANCELLED()) }
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {}
                    ),
                    singleLine = true,
                    trailingIcon = {
                        if (searchValue.text.isNotEmpty()) {
                            Icon(
                                Icons.Rounded.Close,
                                contentDescription = "",
                                Modifier.clickable {
                                    searchValue = TextFieldValue("")
                                    filterItems()
                                }
                            )
                        }
                    },
                )
            }

            LazyColumn {
                items(filteredItems, { it.value }) {
                    DialogDropdownItem(
                        model = it,
                        onClick = {
                            dialogRef.close(DropdownOutput.SELECTED(it.value))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DialogDropdownItem(model: DropdownItemModel, onClick: () -> Unit) {
    Column(
        Modifier.clickable(onClickLabel = model.name, role = Role.Button) { onClick() }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun PreviewDialogDropdownScreen() {
    SalarycomparatorTheme {
        DialogDropdownScreen(
            dialogRef = DialogRef { },
            items = listOf(
                DropdownItemModel("Polish złoty (PLN)", "pln"),
                DropdownItemModel("Euro (EUR)", "eur"),
                DropdownItemModel("United States dollar (USD)", "usd"),
            ),
            value = ""
        )
    }
}
