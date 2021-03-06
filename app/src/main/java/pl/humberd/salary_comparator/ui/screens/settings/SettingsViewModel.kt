package pl.humberd.salary_comparator.ui.screens.settings

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.humberd.salary_comparator.R
import pl.humberd.salary_comparator.services.CurrencyService

class SettingsViewModel : ViewModel() {
    private val _isExchangeRateUpdateInProgress = mutableStateOf(false)
    val isExchangeRateUpdateInProgress: State<Boolean> = _isExchangeRateUpdateInProgress

    fun updateExchangeRate(context: Context, scaffoldState: ScaffoldState) {
        viewModelScope.launch(Dispatchers.IO) {
            _isExchangeRateUpdateInProgress.value = true
            try {
                CurrencyService.updateFromApi(context)
            } catch (e: Exception) {
                launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = e.message ?: context.getString(R.string.snackbar_update_exchange_rate_default_message),
                        actionLabel = context.getString(R.string.snackbar_action_close)
                    )
                }
            } finally {
                _isExchangeRateUpdateInProgress.value = false
            }
        }
    }
}
