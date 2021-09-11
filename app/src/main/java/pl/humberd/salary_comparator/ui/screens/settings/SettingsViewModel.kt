package pl.humberd.salary_comparator.ui.screens.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.humberd.salary_comparator.services.CurrencyService

class SettingsViewModel: ViewModel() {
    fun updateExchangeRate(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            CurrencyService.updateFromApi(context)
        }
    }
}
