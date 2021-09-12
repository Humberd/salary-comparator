package pl.humberd.salary_comparator.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import pl.humberd.salary_comparator.R

sealed class Screen(
    val route: String,
    @StringRes private val nameRes: Int,
    @DrawableRes private val icon: Int
) {
    object CONVERTER_FORM : Screen(
        route = "converter_form",
        nameRes = R.string.screens_converter_name,
        icon = R.drawable.ic_baseline_calculate_24
    )

    object SETTINGS : Screen(
        route = "settings",
        nameRes = R.string.screens_settings_name,
        icon = R.drawable.ic_baseline_settings_24
    )
    companion object {
        fun findByRoute(route: String): Screen {
            return when (route) {
                CONVERTER_FORM.route -> CONVERTER_FORM
                SETTINGS.route -> SETTINGS
                else -> throw Error("Cannot find a screen by a route name")
            }
        }
    }

    @Composable
    fun getName() = stringResource(nameRes)
    @Composable
    fun getIcon() = painterResource(icon)
}

data class DialogRef<in OUTPUT>(
    private val onClose: (OUTPUT) -> Unit
) {
    fun close(result: OUTPUT) {
        onClose(result)
    }
}

sealed class DropdownOutput {
    class CANCELLED : DropdownOutput()
    class SELECTED(val id: String) : DropdownOutput()
}

sealed class Dialog(val route: String) {
    object DROPDOWN : Dialog("dropdown") {
        var content: (@Composable () -> Unit)? = null
            private set

        fun open(
            navController: NavController,
            onClose: (DropdownOutput) -> Unit = {},
            content: @Composable (DialogRef<DropdownOutput>) -> Unit
        ) {
            val dialogRef = DialogRef<DropdownOutput> {
                navController.popBackStack()
                onClose(it)
                this.content = null
            }

            this.content = {
                content(dialogRef)
            }

            navController.navigate(
                route = "dropdown"
            )
        }
    }
}
