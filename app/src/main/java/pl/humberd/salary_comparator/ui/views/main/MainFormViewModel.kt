package pl.humberd.salary_comparator.ui.views.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.humberd.salary_comparator.ui.components.AmountUnit

class MainFormViewModel : ViewModel() {
    private val _sourceCurrency = MutableLiveData("EUR")
    val sourceCurrency: LiveData<String> = _sourceCurrency

    private val _targetCurrency = MutableLiveData("PLN")
    val targetCurrency: LiveData<String> = _targetCurrency

    private val _amount = MutableLiveData("4500")
    val amount: LiveData<String> = _amount

    private val _unit = MutableLiveData(AmountUnit.MONTH)
    val unit: LiveData<AmountUnit> = _unit

    private val _result = MutableLiveData<Map<AmountUnit, List<Pair<Currency, Int>>>>(emptyMap())
    val result: LiveData<Map<AmountUnit, List<Pair<Currency, Int>>>> = _result

    fun updateSourceCurrency(new: String) {
        _sourceCurrency.value = new
    }

    fun updateTargetCurrency(new: String) {
        _targetCurrency.value = new
    }

    fun updateValue(new: String) {
        _amount.value = new
    }

    fun updateUnit(unit: String) {
        _unit.value = AmountUnit.valueOf(unit)
    }

    fun swap() {
        val source = sourceCurrency.value
        _sourceCurrency.value = targetCurrency.value
        _targetCurrency.value = source
    }

    fun convert() {
        val map = AmountUnit.values().map {
            it to listOf(
                Pair(sourceCurrency.value.orEmpty(), 0),
                Pair(targetCurrency.value.orEmpty(), 0)
            )
        }.toTypedArray()

        _result.value = mapOf(*map)
    }
}
