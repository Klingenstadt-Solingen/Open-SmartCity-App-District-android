package de.osca.android.district.core.data.model

import com.parse.ParseObject
import java.util.Date
import kotlin.reflect.KProperty

class NullableDateParseDelegate(private val name: String?) {
    operator fun getValue(
        parseObject: ParseObject,
        property: KProperty<*>,
    ): Date? {
        return parseObject.getDate(name ?: property.name)
    }

    operator fun setValue(
        parseObject: ParseObject,
        property: KProperty<*>,
        value: Date?,
    ) {
        value?.let {
            parseObject.put(name ?: property.name, it)
        }
    }
}

inline fun nullableDateAttribute(name: String? = null) = NullableDateParseDelegate(name)
