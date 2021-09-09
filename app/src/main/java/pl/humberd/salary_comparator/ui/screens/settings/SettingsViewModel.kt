package pl.humberd.salary_comparator.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.humberd.salary_comparator.services.CurrencyService

class SettingsViewModel: ViewModel() {
    fun updateExchangeRate() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = CurrencyService.fetchNewestData()
            println(result)
        }
    }
}
