package pl.humberd.salary_comparator.store

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import pl.humberd.salary_comparator.proto.ConverterFormStateOuterClass.ConverterFormState
import pl.humberd.salary_comparator.proto.converterFormState
import pl.humberd.salary_comparator.services.AmountUnit
import java.io.InputStream
import java.io.OutputStream

val Context.converterFormStateDataStore by dataStore(
    "converterFormState.pb",
    ConverterFormStateSerializer
)

object ConverterFormStateSerializer : Serializer<ConverterFormState> {
    override val defaultValue: ConverterFormState = converterFormState {
        sourceCurrency = "eur"
        targetCurrency = "usd"
        amount = ""
        amountUnit = AmountUnit.MONTH.name
    }

    override suspend fun writeTo(t: ConverterFormState, output: OutputStream) {
        t?.writeTo(output)
    }

    override suspend fun readFrom(input: InputStream): ConverterFormState {
        try {
            return ConverterFormState.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }
}
