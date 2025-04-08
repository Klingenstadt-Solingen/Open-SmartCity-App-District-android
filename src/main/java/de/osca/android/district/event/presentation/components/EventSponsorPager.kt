package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.LoadingImage
import de.osca.android.district.core.presentation.component.pager.Pager
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.event.data.model.EventSponsor

@Composable
fun EventSponsorPager(
    sponsors: List<EventSponsor>,
) {
    Column(
        Modifier
            .height(270.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.sponsors),
                style =
                    defaultTextStyle(
                        DistrictDesign.Size.Font.SUB_TITLE,
                        Color.White,
                        fontWeight = FontWeight.Bold,
                    ),
                modifier =
                    Modifier.padding(
                        horizontal = DistrictDesign.Padding.BIGGER,
                        vertical = DistrictDesign.Padding.MEDIUM,
                    ),
            )
            Pager(
                list = sponsors,
            ) { sponsor ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = DistrictDesign.Padding.BIG),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        onClick = {

                        },
                        modifier =
                            Modifier
                                .clip(DistrictDesign.ROUNDED_SHAPE),
                    ) {
                        Box(contentAlignment = Alignment.TopEnd) {
                            LoadingImage(
                                url = sponsor.icon.url,
                                modifier =
                                Modifier
                                    .height(160.dp)
                                    .background(Color.White)
                                    .padding(DistrictDesign.Padding.SMALL)
                                    .defaultMinSize(minWidth = 100.dp),
                            )
                        }
                    }
                    Text(
                        text = sponsor.name,
                        style = defaultTextStyle(DistrictDesign.Size.Font.NORMAL_TEXT, Color.White),
                        maxLines = 2,
                        modifier =
                            Modifier
                                .padding(top = DistrictDesign.Padding.SMALL)
                                .fillMaxWidth(),
                    )
                }
            }
        }
    }
}
