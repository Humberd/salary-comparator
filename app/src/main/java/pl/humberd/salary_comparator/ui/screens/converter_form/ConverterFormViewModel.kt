package pl.humberd.salary_comparator.ui.screens.converter_form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.ui.components.AmountUnit
import pl.humberd.salary_comparator.ui.screens.converter_form.components.Currency
import java.util.*

class ConverterFormViewModel : ViewModel() {
    private val _sourceCurrency = MutableLiveData("EUR")
    val sourceCurrency: LiveData<String> = _sourceCurrency

    private val _targetCurrency = MutableLiveData("PLN")
    val targetCurrency: LiveData<String> = _targetCurrency

    private val _amount = MutableLiveData("4500")
    val amount: LiveData<String> = _amount

    private val _unit = MutableLiveData(AmountUnit.MONTH)
    val unit: LiveData<AmountUnit> = _unit

    private val _result = MutableLiveData<Map<AmountUnit, List<Pair<Currency, Float>>>>(emptyMap())
    val result: LiveData<Map<AmountUnit, List<Pair<Currency, Float>>>> = _result

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
        val sanitizedAmount = amount.value?.toInt() ?: throw Error()
        val sanitizedUnit = unit.value ?: throw Error()
        val amountPerHour = sanitizedAmount / sanitizedUnit.hours.toFloat()

        val map = AmountUnit.values().map {
            val source = sourceCurrency.value.orEmpty().lowercase(Locale.getDefault())
            val target = targetCurrency.value.orEmpty().lowercase(Locale.getDefault())

            val eur = CurrencyService.get("eur")!!
            it to listOf(
                Pair(source, it.hours * amountPerHour * (CurrencyService.get(source)!!.getRate() / eur.getRate())),
                Pair(target, it.hours * amountPerHour * (CurrencyService.get(target)!!.getRate() / eur.getRate()))
            )
        }.toTypedArray()

        _result.value = mapOf(*map)
    }

    private fun foo() {

    }
}
