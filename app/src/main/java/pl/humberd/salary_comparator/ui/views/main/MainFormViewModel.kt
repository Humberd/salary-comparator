package pl.humberd.salary_comparator.ui.views.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFormViewModel : ViewModel() {
    private val _sourceCurrency = MutableLiveData("PLN")
    val sourceCurrency: LiveData<String> = _sourceCurrency

    private val _targetCurrency = MutableLiveData("EUR")
    val targetCurrency: LiveData<String> = _targetCurrency

    private val _rawValue = MutableLiveData("0")
    val rawValue: LiveData<String> = _rawValue

    fun updateSourceCurrency(new: String) {
        _sourceCurrency.value = new
    }

    fun updateTargetCurrency(new: String) {
        _targetCurrency.value = new
    }

    fun updateValue(new: String) {
        _rawValue.value = new
    }

    fun swap() {
        val source = sourceCurrency.value
        _sourceCurrency.value = targetCurrency.value
        _targetCurrency.value = source
    }
}
