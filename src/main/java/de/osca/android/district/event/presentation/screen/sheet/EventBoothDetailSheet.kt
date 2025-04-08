package de.osca.android.district.event.presentation.screen.sheet


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.halilibo.richtext.commonmark.Markdown
import com.halilibo.richtext.ui.RichTextScope
import de.osca.android.district.core.PRIMARY_COLOR
import de.osca.android.district.core.presentation.component.LoadingImage
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.event.data.model.EventBooth
import de.osca.android.district.event.presentation.components.EventLocationNavigation
import de.osca.android.district.event.presentation.components.EventSponsorPager
import de.osca.android.district.event.viewmodel.EventBoothDetailViewModel

@Composable
fun EventBoothDetailSheet(
    selectedEventBooth: EventBooth,
) {
    val viewModel: EventBoothDetailViewModel = hiltViewModel()

    LaunchedEffect(selectedEventBooth) {
        viewModel.requestTags(selectedEventBooth.objectId)
        viewModel.requestSponsors(selectedEventBooth.objectId)
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
    ) {
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(DistrictDesign.Padding.BIGGER)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier.fillMaxWidth(0.5f), verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT)) {
                        Text(selectedEventBooth.type?.name ?: "", fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)
                        Text(selectedEventBooth.name, style = defaultTextStyle(
                            DistrictDesign.Size.Font.HEADLINE,
                            fontWeight = FontWeight.Bold,
                        ), maxLines = 3)

                        Text(
                            (viewModel.eventTags.map { eventTag ->
                                eventTag.name
                            }).joinToString(separator = ", "),
                            fontSize = DistrictDesign.Size.Font.NORMAL_TEXT
                        )
                    }
                    selectedEventBooth.mainSponsor?.let { mainSponsor ->
                        LoadingImage(
                            contentScale = ContentScale.Fit,
                            url = mainSponsor.icon.url,
                            modifier = Modifier
                                .height(100.dp)
                        )
                    }
                }

                Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG)) {
                    EventLocationNavigation(selectedEventBooth.locationDescription, selectedEventBooth.geopoint)
                    selectedEventBooth.content?.let { content ->
                        RichTextScope.Markdown(content = content.trimIndent())
                    }
                }
            }

        }
        item {
            if(viewModel.eventSponsors.size > 0){
                Column(
                    Modifier
                        .background(PRIMARY_COLOR)
                        .fillMaxWidth()
                        .padding(bottom = DistrictDesign.Padding.MEDIUM),
                ) {
                    EventSponsorPager(viewModel.eventSponsors)
                }
            }
        }
    }
}
