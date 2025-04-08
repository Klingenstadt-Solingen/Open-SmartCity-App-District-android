package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.button.GeneralButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.accentColor
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.core.util.startCalendarIntent
import de.osca.android.district.core.util.toLocalDateTime
import de.osca.android.district.event.data.model.Event

@Composable
fun CalendarButton(
    modifier: Modifier = Modifier,
    event: Event,
) {
    val context = LocalContext.current
    GeneralButton(
        modifier = modifier,
        contentPadding = PaddingValues(DistrictDesign.Padding.MEDIUM),
        colors = CardDefaults.cardColors(containerColor = Color.accentColor),
        shape = DistrictDesign.ROUNDED_SHAPE,
        onClick = {
            startCalendarIntent(
                title = event.name,
                description = event.description,
                startDateTime = event.startDate.toLocalDateTime(),
                endDateTime = event.endDate?.toLocalDateTime(),
                context = context,
            )
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "calendar button",
            modifier = Modifier.size(DistrictDesign.Size.Icon.BIG),
            tint = Color.primary,
        )
    }
}
