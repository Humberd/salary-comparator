package pl.humberd.salary_comparator.ui.screens

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

data class DialogRef<out INPUT, in OUTPUT>(
    val input: INPUT,
    private val onClose: (OUTPUT) -> Unit
) {
    fun close(result: OUTPUT) {
        onClose(result)
    }
}

data class DropdownInput(val selectedId: String)
sealed class DropdownOutput {
    class CANCELLED : DropdownOutput()
    class SELECTED(val id: String): DropdownOutput()
}

sealed class Dialog(val route: String) {
    object DROPDOWN : Dialog("dropdown") {
        var result: DialogRef<DropdownInput, DropdownOutput>? = null
            private set

        fun open(navController: NavController, input: DropdownInput, onClose: (DropdownOutput) -> Unit = {}) {
            result = DialogRef(input) {
                result = null
                navController.popBackStack()
                onClose(it)
            }

            navController.navigate(
                route = "dropdown"
            )
        }
    }
}
