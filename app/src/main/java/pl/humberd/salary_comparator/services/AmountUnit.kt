package pl.humberd.salary_comparator.services

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pl.humberd.salary_comparator.R

enum class AmountUnit(
    val hours: Int,
    @StringRes private val nameRes: Int
) {
    HOUR(1, R.string.unit_name_hour),
    DAY(HOUR.hours * 8, R.string.unit_name_day),
    MONTH(DAY.hours * 20, R.string.unit_name_month),
    YEAR(MONTH.hours * 12, R.string.unit_name_year);

    @Composable
    fun getName() = stringResource(nameRes)
}
