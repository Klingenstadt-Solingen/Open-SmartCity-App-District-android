package de.osca.android.district.core.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import de.osca.android.essentials.presentation.component.calendar.AddEventToSystemCalendar
import java.time.LocalDateTime

fun startShareIntent(
    url: String?,
    context: Context,
) {
    if (!(url.isNullOrBlank())) {
        val sendIntent =
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }
        context.startActivity(sendIntent)
    }
}

fun startLinkIntent(
    url: String?,
    context: Context,
) {
    if (url?.isNotBlank() == true) {
        val uri = Uri.parse(url)

        val linkIntent =
            Intent(
                when (uri.scheme) {
                    "tel:" -> Intent.ACTION_DIAL
                    "mailto:" -> Intent.ACTION_SENDTO
                    else -> Intent.ACTION_VIEW
                },
                uri,
            )
        context.startActivity(linkIntent)
    }
}

fun startCalendarIntent(
    title: String,
    description: String? = null,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime?,
    context: Context,
) {
    val calendarIntent =
        AddEventToSystemCalendar(
            title = title,
            description = description ?: "",
            startDateTime = startDateTime,
            endDateTime = endDateTime,
        )
    context.startActivity(calendarIntent)
}

fun startMapIntent(
    position: LatLng,
    context: Context,
) {
    val mapsIntentUri = Uri.parse("http://maps.google.com/maps?daddr=${position.latitude},${position.longitude}")
    val mapIntent = Intent(Intent.ACTION_VIEW, mapsIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}