package pl.humberd.salary_comparator.store

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import pl.humberd.salary_comparator.proto.CurrencyRateOuterClass
import java.io.InputStream
import java.io.OutputStream

val Context.currencyRateDataStore by dataStore(
    "currencyRate.pb",
    CurrencyRateSerializer
)

object CurrencyRateSerializer : Serializer<CurrencyRateOuterClass.CurrencyRate?> {
    override val defaultValue: CurrencyRateOuterClass.CurrencyRate? = null

    override suspend fun readFrom(input: InputStream): CurrencyRateOuterClass.CurrencyRate? {
        try {
            return CurrencyRateOuterClass.CurrencyRate.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: CurrencyRateOuterClass.CurrencyRate?, output: OutputStream) {
        t?.writeTo(output)
    }
}
