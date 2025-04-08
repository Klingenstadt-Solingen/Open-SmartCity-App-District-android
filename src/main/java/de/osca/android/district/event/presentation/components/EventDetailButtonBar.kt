package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.button.GeneralButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.accentColor
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.core.util.startShareIntent
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.viewmodel.EventViewModel

@Composable
fun EventDetailButtonBar(
    event: Event,
    eventViewModel: EventViewModel = sharedHiltViewModel()
) {
    val context = LocalContext.current
    val isFavorite =  eventViewModel.favorites.value.contains(event.objectId)

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = DistrictDesign.Padding.HUGE,
                ),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
            modifier = Modifier.padding(top = DistrictDesign.Padding.SMALLEST),
        ) {
            FavoriteButton(
                modifier = Modifier.size(DistrictDesign.Size.Icon.BIGGER),
                isFavorite = isFavorite,
                onClick = { eventViewModel.toggleEvent(event) },
            )
            Text(
                stringResource(R.string.bookmark_title),
                fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                modifier = Modifier.padding(DistrictDesign.Padding.SMALLEST),
            )
        }

        if (event.url?.isNotBlank() == true) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
            ) {
                GeneralButton(
                    contentPadding = PaddingValues(DistrictDesign.Padding.MEDIUM),
                    colors = CardDefaults.cardColors(containerColor = Color.accentColor),
                    shape = DistrictDesign.ROUNDED_SHAPE,
                    onClick = { startShareIntent(event.url, context) },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = null,

                        tint = Color.primary,
                        modifier =
                            Modifier
                                .size(DistrictDesign.Size.Icon.BIG)
                    )
                }
                Text(
                    stringResource(R.string.share_title),
                    fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
        ) {
            CalendarButton(event = event)
            Text(
                stringResource(R.string.calendar_title),
                fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
            )
        }
    }
}
