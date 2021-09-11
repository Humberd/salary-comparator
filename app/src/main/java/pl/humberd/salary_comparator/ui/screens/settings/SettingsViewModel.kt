package pl.humberd.salary_comparator.ui.screens.settings

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.humberd.salary_comparator.services.CurrencyService

class SettingsViewModel : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun updateExchangeRate(context: Context, scaffoldState: ScaffoldState) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                CurrencyService.updateFromApi(context)
            } catch (e: Exception) {
                launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = e.message ?: "Something went wrong",
                        actionLabel = "Close"
                    )
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
