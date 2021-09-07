package pl.humberd.salary_comparator.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pl.humberd.salary_comparator.ui.screens.Screen
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun BottomBar() {
    val menuItems = listOf(
        Screen.CONVERTER_FORM,
        Screen.CURRENCY_EXCHANGE
    )

    BottomNavigation {
        menuItems.forEach {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = it.icon),
                        contentDescription = ""
                    )
                },
                label = { Text(it.name) },
                selected = false,
                onClick = { /*TODO*/ }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBottomBar() {
    SalarycomparatorTheme {
        BottomBar()
    }
}
