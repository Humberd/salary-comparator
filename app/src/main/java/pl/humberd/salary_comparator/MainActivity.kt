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
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
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
        WindowCompat.setDecorFitsSystemWindows(window, false)

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
                            e.message
                                ?: context.getString(R.string.screens_settings_options_exchange_rate_action_loading_aria_label),
                            context.getString(R.string.snackbar_load_initial_exchange_rate_default_message)
                        )
                    }
                }
            }

            SalarycomparatorTheme {
                ProvideWindowInsets {
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier
                            .navigationBarsWithImePadding()
                            .statusBarsPadding()
                    ) {
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
                                    ConverterFormScreen(
                                        navController = navController
                                    )
                                }
                                composable(Screen.SETTINGS.route) {
                                    SettingsScreen(
                                        scaffoldState = scaffoldState,
                                        navController = navController
                                    )
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
}
