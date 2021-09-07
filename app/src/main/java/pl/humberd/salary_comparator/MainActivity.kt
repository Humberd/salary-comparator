package pl.humberd.salary_comparator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.humberd.salary_comparator.ui.screens.Screen
import pl.humberd.salary_comparator.ui.screens.currency_exchange.CurrencyExchangeScreen
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme
import pl.humberd.salary_comparator.ui.views.main.ConverterFormScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalarycomparatorTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screen.CONVERTER_FORM.route) {
                        composable(Screen.CONVERTER_FORM.route) {
                            ConverterFormScreen()
                        }
                        composable(Screen.CURRENCY_EXCHANGE.route) {
                            CurrencyExchangeScreen()
                        }
                    }
                }
            }
        }
    }
}
