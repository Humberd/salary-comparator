package pl.humberd.salary_comparator.ui.screens.converter_form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.humberd.salary_comparator.services.AmountUnit
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.screens.converter_form.components.Currency
import java.util.*

class ConverterFormViewModel : ViewModel() {
    private val _sourceCurrency = MutableLiveData("eur")
    val sourceCurrency: LiveData<String> = _sourceCurrency

    private val _targetCurrency = MutableLiveData("pln")
    val targetCurrency: LiveData<String> = _targetCurrency

    private val _amount = MutableLiveData("4500")
    val amount: LiveData<String> = _amount

    private val _unit = MutableLiveData(AmountUnit.MONTH)
    val unit: LiveData<AmountUnit> = _unit

    private val _result = MutableLiveData<Map<AmountUnit, List<Pair<Currency, Double>>>>(emptyMap())
    val result: LiveData<Map<AmountUnit, List<Pair<Currency, Double>>>> = _result

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
        val sourceAmount = amount.value.let {
            check(it != null)
            it.toDoubleOrNull().let {
                check(it != null)
                it
            }
        }
        val sourceUnit = unit.value.let {
            check(it != null)
            it
        }
        val sourceAmountPerHour = sourceAmount / sourceUnit.hours

        val sourceCurrency = this.sourceCurrency.value.let {
            check(it != null)
            it.lowercase(Locale.getDefault())
        }
        val targetCurrency = this.targetCurrency.value.let {
            check(it != null)
            it.lowercase(Locale.getDefault())
        }

        val map = AmountUnit.values().map {
            it to listOf(
                Pair(sourceCurrency, it.hours * sourceAmountPerHour),
                Pair(
                    targetCurrency, it.hours * calculateTargetAmountPerHour(
                        sourceAmountPerHour,
                        CurrencyService.get(sourceCurrency)?.getRate()!!,
                        CurrencyService.get(targetCurrency)?.getRate()!!
                    )
                )
            )
        }.toTypedArray()

        _result.value = mapOf(*map)
    }

    private fun calculateTargetAmountPerHour(
        sourceAmount: Double,
        sourceRate: Double,
        targetRate: Double
    ): Double {
        return (sourceAmount * targetRate) / sourceRate
    }
}
