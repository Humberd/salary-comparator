package pl.humberd.salary_comparator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.components.BottomBar
import pl.humberd.salary_comparator.ui.screens.Dialog
import pl.humberd.salary_comparator.ui.screens.Screen
import pl.humberd.salary_comparator.ui.screens.converter_form.ConverterFormScreen
import pl.humberd.salary_comparator.ui.screens.settings.SettingsScreen
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val context = LocalContext.current

            remember {
                scope.launch(Dispatchers.IO) {
                    try {
                        CurrencyService.init(context)
                    } catch (e: Exception) {
                        scaffoldState.snackbarHostState.showSnackbar(
                            e.message ?: "Failed to load default exchange rate", "Close"
                        )
                    }
                }
            }

            SalarycomparatorTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    Scaffold(
                        scaffoldState = scaffoldState,
                        bottomBar = {
                            BottomBar(navController)
                        },
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.CONVERTER_FORM.route,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.CONVERTER_FORM.route) {
                                ConverterFormScreen(navController = navController)
                            }
                            composable(Screen.SETTINGS.route) {
                                SettingsScreen(scaffoldState = scaffoldState)
                            }
                            dialog(
                                Dialog.DROPDOWN.route,
                                dialogProperties = DialogProperties(usePlatformDefaultWidth = false)
                            ) {
                                Dialog.DROPDOWN.content?.invoke()
                            }
                        }
                    }
                }
            }
        }
    }
}
