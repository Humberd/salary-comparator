package pl.humberd.salary_comparator.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.humberd.salary_comparator.R
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
    backButtonAriaLabel: String = stringResource(R.string.generic_back_button_aria_label),
    searchPlaceholderLabel: String = stringResource(R.string.generic_search_placeholder),
    clearTextButtonAriaLabel: String = stringResource(R.string.generic_text_field_clear_button_aria_label),
    items: List<DropdownItemModel> = emptyList(),
    value: String = "",
    onValueChange: (String) -> Unit = {},
    valueDisplayTransformer: (String) -> String = { it }
) {
    TextButton(
        modifier = Modifier.semantics(mergeDescendants = true) {},
        onClick = {
            Dialog.DROPDOWN.open(
                navController,
                onClose = {
                    when (it) {
                        is DropdownOutput.SELECTED -> onValueChange(it.id)
                    }
                }
            ) {
                DialogDropdownScreen(
                    dialogRef = it,
                    value = value,
                    items = items,
                    backButtonAriaLabel = backButtonAriaLabel,
                    searchPlaceholderLabel = searchPlaceholderLabel,
                    clearTextButtonAriaLabel = clearTextButtonAriaLabel
                )
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
                Text(valueDisplayTransformer(value))
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
    backButtonAriaLabel: String,
    searchPlaceholderLabel: String,
    clearTextButtonAriaLabel: String,
    items: List<DropdownItemModel>
) {

    val scope = rememberCoroutineScope()
    var searchValue by remember { mutableStateOf(TextFieldValue("", TextRange(0))) }
    val filteredItems = remember { items.toMutableStateList() }
    val searchScrollState = rememberLazyListState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    fun filterItems() {
        val searchLc = searchValue.text.lowercase(Locale.getDefault())
        scope.launch {
            filteredItems.clear()
            filteredItems.addAll(
                items.filter { it.name.lowercase(Locale.getDefault()).contains(searchLc) }
            )
            searchScrollState.scrollToItem(0)
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
            TextField(
                value = searchValue,
                onValueChange = {
                    searchValue = it
                    filterItems()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                placeholder = { Text(searchPlaceholderLabel) },
                leadingIcon = {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = backButtonAriaLabel,
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
                            contentDescription = clearTextButtonAriaLabel,
                            Modifier.clickable {
                                searchValue = TextFieldValue("")
                                filterItems()
                            }
                        )
                    }
                },
            )

            LazyColumn(state = searchScrollState) {
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
        Modifier
            .clickable(onClickLabel = model.name, role = Role.Button) { onClick() }
            .semantics(mergeDescendants = true) {}
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (model.icon != null) {
                Icon(
                    painter = painterResource(model.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .border(0.1.dp, MaterialTheme.colors.onSurface),
                    tint = Color.Unspecified
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
            value = "",
            backButtonAriaLabel = "Back",
            searchPlaceholderLabel = "Search...",
            clearTextButtonAriaLabel = "Clear text",
            items = listOf(
                DropdownItemModel("Polish złoty (PLN)", "pln"),
                DropdownItemModel("Euro (EUR)", "eur"),
                DropdownItemModel("United States dollar (USD)", "usd"),
            )
        )
    }
}
