package de.osca.android.district.core.data

fun <T> MutableList<T>.resetWith(elements: Collection<T>?) {
    this.clear()
    this.addAll(elements ?: emptyList())
}
