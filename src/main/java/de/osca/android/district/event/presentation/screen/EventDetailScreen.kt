package de.osca.android.district.event.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.core.PRIMARY_COLOR
import de.osca.android.district.core.presentation.component.HtmlText
import de.osca.android.district.core.presentation.component.LoadingImage
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.event.data.model.EventDetails
import de.osca.android.district.event.presentation.components.EventDetailButtonBar
import de.osca.android.district.event.presentation.components.EventDetailHead
import de.osca.android.district.event.presentation.components.EventDetailPocketMap
import de.osca.android.district.event.presentation.components.EventLocationNavigation
import de.osca.android.district.event.presentation.components.EventOpeningHours
import de.osca.android.district.event.presentation.components.EventPrices
import de.osca.android.district.event.presentation.components.EventSponsorPager
import de.osca.android.district.event.viewmodel.EventDetailViewModel

@Composable
fun EventDetailScreen(
    objectId: String,
    eventDetailViewModel: EventDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(objectId) {
        eventDetailViewModel.requestEventData(objectId)

    }

    TopBarScaffold { paddingValues ->
        LoadingWrapper(
            loadingState = eventDetailViewModel.loadingState,
        ) { event ->
            val details = EventDetails(event)
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = DistrictDesign.Padding.BIGGER),
                        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
                    ) {
                        EventDetailHead(event = event)
                        if (!event.image.isNullOrBlank()) {
                            LoadingImage(
                                url = event.image!!,
                                modifier =
                                    Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .height(200.dp)
                                        // TODO: For accessibility it would be required to give a description of the image via the api
                                        .clip(DistrictDesign.ROUNDED_SHAPE)
                                        .clearAndSetSemantics {
                                            contentDescription = ""
                                            role = Role.Image
                                        },
                                contentScale = ContentScale.FillHeight,
                            )
                        }

                        HorizontalDivider(
                            thickness = DistrictDesign.Thickness.DEFAULT,
                            color = Color.primary,
                        )
                        EventDetailButtonBar(event = event)
                        HorizontalDivider(
                            thickness = DistrictDesign.Thickness.DEFAULT,
                            color = Color.primary,
                        )

                        event.description?.let {
                            HtmlText(htmlString = it)
                        }
                        if (details.prices.isNotEmpty()) {
                            EventPrices(
                                details.prices,
                            )
                        }
                        EventOpeningHours()
                        EventLocationNavigation(details.name, event.geopoint)
                    }
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = DistrictDesign.Padding.BIGGER)
                            .padding(bottom = DistrictDesign.Padding.MEDIUM),
                        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
                    ) {
                        EventDetailPocketMap(
                            event = event,
                            eventBooths = eventDetailViewModel.eventBooths,
                        )
                    }
                }

                item {
                    if (eventDetailViewModel.eventSponsors.size > 0) {
                        Column(
                            Modifier
                                .background(PRIMARY_COLOR)
                                .fillMaxWidth(),
                        ) {
                            EventSponsorPager(eventDetailViewModel.eventSponsors)
                        }
                    }
                }
            }
        }
    }
}
