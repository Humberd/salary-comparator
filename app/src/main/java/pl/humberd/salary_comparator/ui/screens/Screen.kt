package pl.humberd.salary_comparator.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import pl.humberd.salary_comparator.R

sealed class Screen(val route: String, val name: String, val icon: Int) {
    object CONVERTER_FORM : Screen(
        route = "converter_form",
        name = "Converter",
        icon = R.drawable.ic_baseline_calculate_24
    )

    object SETTINGS : Screen(
        route = "settings",
        name = "Settings",
        icon = R.drawable.ic_baseline_settings_24
    )
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
    class SELECTED(val id: String): DropdownOutput()
}

sealed class Dialog(val route: String) {
    object DROPDOWN : Dialog("dropdown") {
        var content: (@Composable () -> Unit)? = null
            private set

        fun open(navController: NavController, onClose: (DropdownOutput) -> Unit = {}, content: @Composable (DialogRef<DropdownOutput>) -> Unit) {
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
