package pl.humberd.salary_comparator.services

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET

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
