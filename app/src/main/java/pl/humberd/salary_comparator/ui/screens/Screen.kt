package pl.humberd.salary_comparator.ui.screens

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
