package pl.humberd.salary_comparator.ui.screens.converter_form

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.humberd.salary_comparator.proto.ConverterFormStateOuterClass.ConverterFormState
import pl.humberd.salary_comparator.services.AmountUnit
import pl.humberd.salary_comparator.services.CurrencyService
import pl.humberd.salary_comparator.store.converterFormStateDataStore
import pl.humberd.salary_comparator.ui.screens.converter_form.components.Currency
import java.util.*

class ConverterFormViewModel : ViewModel() {
    private val _result = MutableLiveData<Map<AmountUnit, List<Pair<Currency, Double>>>>(emptyMap())
    val result: LiveData<Map<AmountUnit, List<Pair<Currency, Double>>>> = _result

    fun swap(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            context.converterFormStateDataStore.updateData {
                it.toBuilder().apply {
                    sourceCurrency = it.targetCurrency
                    targetCurrency = it.sourceCurrency
                }.build()
            }
        }
    }

    fun convert(state: ConverterFormState) {
        val sourceAmount = state.amount.let {
            check(it != null)
            if (it.isEmpty()) {
                return
            }
            val value = it
                .replace(",", ".")
                .replace("-", "")
                .replace(" ", "")
            value.toDoubleOrNull().let {
                if (it == null) {
                    return
                }
                it
            }
        }
        val sourceUnit = state.amountUnit.let {
            check(it != null)
            AmountUnit.valueOf(it)
        }
        val sourceAmountPerHour = sourceAmount / sourceUnit.hours

        val sourceCurrency = state.sourceCurrency.let {
            check(it != null)
            it.lowercase(Locale.getDefault())
        }
        val targetCurrency = state.targetCurrency.let {
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
