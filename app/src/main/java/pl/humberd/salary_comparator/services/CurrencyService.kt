package pl.humberd.salary_comparator.services

import android.content.Context
import com.murgupluoglu.flagkit.FlagKit

data class CurrencyModel(
    val name: String,
    val icon: Int
)

object CurrencyService {
    fun getAvailableCurrencies(context: Context): List<CurrencyModel> {
        return listOf(
            CurrencyModel("CHF", FlagKit.getResId(context, "ch")),
            CurrencyModel("EUR", FlagKit.getResId(context, "eu")),
            CurrencyModel("GBP", FlagKit.getResId(context, "gb")),
            CurrencyModel("PLN", FlagKit.getResId(context, "pl")),
            CurrencyModel("USD", FlagKit.getResId(context, "us")),
        )
    }
}
