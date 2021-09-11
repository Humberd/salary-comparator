package pl.humberd.salary_comparator.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.humberd.salary_comparator.ui.screens.Screen
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme

@Composable
fun BottomBar(navController: NavController) {
    val menuItems = listOf(
        Screen.CONVERTER_FORM,
        Screen.SETTINGS
    )

    BottomNavigation {
        menuItems.forEach {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = it.icon),
                        contentDescription = ""
                    )
                },
                label = { Text(it.name) },
                selected = currentDestination?.hierarchy?.any { view -> view.route == it.route } == true,
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBottomBar() {
    SalarycomparatorTheme {
        val navController = rememberNavController()

        BottomBar(navController)
    }
}
