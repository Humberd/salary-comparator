package pl.humberd.salary_comparator.ui.components

enum class AmountUnit(val hours: Int) {
    HOUR(1),
    DAY(HOUR.hours * 8),
    MONTH(DAY.hours * 20),
    YEAR(MONTH.hours * 12);
}
