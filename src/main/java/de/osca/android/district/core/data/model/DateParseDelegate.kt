package de.osca.android.district.core.data.model

import com.parse.ParseObject
import java.util.Date
import kotlin.reflect.KProperty

// Use only on required Dates!
class DateParseDelegate(private val name: String?) {
    operator fun getValue(
        parseObject: ParseObject,
        property: KProperty<*>,
    ): Date {
        return parseObject.getDate(name ?: property.name)!!
    }

    operator fun setValue(
        parseObject: ParseObject,
        property: KProperty<*>,
        value: Date,
    ) {
        parseObject.put(name ?: property.name, value ?: Date())
    }
}

inline fun dateAttribute(name: String? = null) = DateParseDelegate(name)
