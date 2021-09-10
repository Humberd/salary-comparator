package pl.humberd.salary_comparator.services

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.protobuf.util.JsonFormat
import pl.humberd.salary_comparator.proto.CurrencyRateOuterClass
import pl.humberd.salary_comparator.ui.components.CURRENCIES
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

interface CurrencyRepository {
    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/eur.json")
    fun fetchCurrencies(): Call<String>

    companion object {
        val instance = Retrofit.Builder()
            .baseUrl("https://cdn.jsdelivr.net")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create<CurrencyRepository>()
    }
}

object CurrencyService {
    private val cacheMap = CURRENCIES.associate { it.id to it }
    private val cacheList = cacheMap.values

    private val _lastUpdate = mutableStateOf<String?>("")
    val lastUpdate: State<String?> = _lastUpdate

    fun getCurrencies() = cacheList

    fun get(id: String) = cacheMap[id]

    fun init(context: Context) {
        val jsonString = readInitialRatesJson(context)
        val entity = generateModel(jsonString)

        entity.eurMap.forEach {
            cacheMap[it.key]?.updateRate(it.value)
        }

        _lastUpdate.value = entity.date
    }

    fun updateFromApi() {
        val jsonString = readCurrentRatesFromAPI()
        val entity = generateModel(jsonString)

        entity.eurMap.forEach {
            cacheMap[it.key]?.updateRate(it.value)
        }

        _lastUpdate.value = entity.date
    }

    private fun generateModel(jsonString: String): CurrencyRateOuterClass.CurrencyRate {
        val builder = CurrencyRateOuterClass.CurrencyRate.newBuilder()
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
