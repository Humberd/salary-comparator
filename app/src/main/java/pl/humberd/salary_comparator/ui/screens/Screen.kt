package pl.humberd.salary_comparator.ui.screens

sealed class Screen(val route: String) {
    object CONVERTER_FORM : Screen("converter_form")
    object CURRENCY_EXCHANGE : Screen("currency_exchange")
}
