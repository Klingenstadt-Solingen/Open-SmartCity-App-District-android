package de.osca.android.district.core.data.model

import com.parse.ParseObject
import kotlin.reflect.KProperty

inline fun <T : ParseObject> pointerAttribute(name: String? = null) = PointerDelegate<T>(name)

class PointerDelegate<T : ParseObject>(
    private val key: String? = null,
) {
    operator fun getValue(
        thisRef: ParseObject,
        property: KProperty<*>,
    ): T? = thisRef.getParseObject(key ?: property.name)?.fetchIfNeeded<T>()

    operator fun setValue(
        thisRef: ParseObject,
        property: KProperty<*>,
        value: T,
    ) {
        thisRef.put(key ?: property.name, value)
    }
}
