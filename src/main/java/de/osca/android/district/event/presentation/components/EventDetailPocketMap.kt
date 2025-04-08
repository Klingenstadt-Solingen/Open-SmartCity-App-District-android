package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import de.osca.android.district.core.navigation.LocalNavigationController
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.data.model.EventBooth
import de.osca.android.district.event.navigation.EventNavItems
import de.osca.android.essentials.domain.entity.Coordinates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailPocketMap(
    modifier: Modifier = Modifier,
    event: Event,
    eventBooths: List<EventBooth>?,
) {
    val navController = LocalNavigationController.current
    Map(
        onClick = {
            if (!eventBooths.isNullOrEmpty()) {
                navController.navigate(
                    EventNavItems.EventDetailMapNavItem(
                        event.objectId,
                    ),
                )
            }
        },
        eventLocation =
            Coordinates(
                latitude = event.geopoint.latitude,
                longitude = event.geopoint.longitude,
            ),
        eventBooths = eventBooths,
        modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(DistrictDesign.ROUNDED_SHAPE),
    )
}
