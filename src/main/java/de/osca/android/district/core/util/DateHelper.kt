package de.osca.android.district.core.util

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale

fun getLocale(): Locale {
    val locales = Resources.getSystem().configuration.locales
    return if (locales.isEmpty) {
        Locale.GERMANY
    } else {
        locales.get(0)
    }
}

fun Date.formatMonthYear(): String = SimpleDateFormat("MMMM yyyy", getLocale()).format(this)

fun Date.formatDayDotMonthNameYear(): String = SimpleDateFormat("dd. MMMM yyyy", getLocale()).format(this)

fun Date.formatDayMonthYear(): String = SimpleDateFormat("dd.MM.yyyy", getLocale()).format(this)

fun Date.formatWeekday(): String = SimpleDateFormat("EEEE dd.MM.yyyy", getLocale()).format(this)

fun Date.formatHourMinute(): String = SimpleDateFormat("HH:mm", getLocale()).format(this)

fun LocalDateTime.atSameDay(other: LocalDateTime): Boolean = year == other.year && month == other.month && dayOfMonth == other.dayOfMonth

/**
 * @return true if all [dates] are on the same day as [this]
 */
fun LocalDateTime.isSameDay(dates: List<LocalDateTime>): Boolean {
    for (date in dates) {
        if (!this.atSameDay(date)) return false
    }

    return true
}

fun LocalDateTime.toUtcDate(): Date = Date.from(toInstant(ZoneOffset.UTC))

fun LocalDateTime.toDate(): Date = Date.from(atZone(ZoneId.systemDefault()).toInstant())

fun Date.toLocalDate(): LocalDate = toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

fun Date.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault())

fun LocalDate.atEndOfDay(): LocalDateTime = LocalDateTime.of(this, LocalTime.MAX)
