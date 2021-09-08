package pl.humberd.salary_comparator.services

import android.content.Context
import org.json.JSONObject
import pl.humberd.salary_comparator.ui.components.CURRENCIES
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object CurrencyService {
    private val cacheMap = CURRENCIES.associate { it.id to it }
    private val cacheList = cacheMap.values

    private var lastUpdate = ""

    fun getCurrencies() = cacheList

    fun get(id: String) = cacheMap[id]

    fun updateFromJsonFile(context: Context) {
        val json = try {
            val stream: InputStream = context.getAssets().open("initial_currency_rate.json")
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            throw ex
        }

        updateRatesWith(JSONObject(json))
    }

    private fun updateRatesWith(obj: JSONObject) {
        val eurRates = obj.getJSONObject("eur")
        lastUpdate = obj.getString("date")
        eurRates.keys().forEach {
            val rate = eurRates.getDouble(it)
            cacheMap[it]?.updateRate(rate)
        }
    }
}
