package pl.humberd.salary_comparator.services

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.protobuf.util.JsonFormat
import kotlinx.coroutines.flow.collect
import pl.humberd.salary_comparator.proto.CurrencyRateOuterClass.CurrencyRate
import pl.humberd.salary_comparator.store.currencyRateDataStore
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object CurrencyService {
    private val cacheMap = CURRENCIES.associate { it.id to it }
    private val cacheList = cacheMap.values

    private val _lastUpdate = mutableStateOf<String?>("")
    val lastUpdate: State<String?> = _lastUpdate

    fun getCurrencies() = cacheList

    fun get(id: String) = cacheMap[id]

    suspend fun init(context: Context) {
        context.currencyRateDataStore.data.collect {
            // valueAlreadyInStore
            if (it != null) {
                it.eurMap.forEach {
                    cacheMap[it.key]?.updateRate(it.value)
                }

                _lastUpdate.value = it.date

                return@collect
            }

            val jsonString = readInitialRatesJson(context)
            updateRates(jsonString, context)
        }
    }

    suspend fun updateFromApi(context: Context) {
        val jsonString = readCurrentRatesFromAPI()
        updateRates(jsonString, context)
    }

    private suspend fun updateRates(jsonString: String, context: Context) {
        val entity = generateModel(jsonString)

        entity.eurMap.forEach {
            cacheMap[it.key]?.updateRate(it.value)
        }

        _lastUpdate.value = entity.date

        context.currencyRateDataStore.updateData {
            entity
        }
    }

    private fun generateModel(jsonString: String): CurrencyRate {
        val builder = CurrencyRate.newBuilder()
        JsonFormat.parser().ignoringUnknownFields().merge(jsonString, builder)
        val entity = builder.build()
        check(entity.date.isNotEmpty())
        check(entity.eurMap.isNotEmpty())
        return entity
    }

    private fun readInitialRatesJson(context: Context): String {
        return try {
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
    }

    private fun readCurrentRatesFromAPI(): String {
        val response = CurrencyRepository.instance.fetchCurrencies().execute().body()
        if (response == null) {
            throw Error("Empty body")
        }

        return response
    }
}
