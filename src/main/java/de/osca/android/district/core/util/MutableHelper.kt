package de.osca.android.district.core.util

fun <T> MutableList<T>.replaceWith(elements: Collection<T>?) {
    this.clear()
    this.addAll(elements ?: emptyList())
}
