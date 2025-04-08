package de.osca.android.district.project.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.SECONDARY_COLOR
import de.osca.android.district.core.presentation.component.LoadingImage
import de.osca.android.district.core.presentation.component.pager.Pager
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.util.startLinkIntent
import de.osca.android.district.project.data.model.ProjectPartner
import de.osca.android.district.project.data.model.ProjectPartnerCategory

@Composable
fun ProjectPartnerPager(
    partnerCategory: ProjectPartnerCategory,
    partners: List<ProjectPartner>,
) {
    val context = LocalContext.current
    val semanticOpenLink = stringResource(id = R.string.open_link)

    Column(
        Modifier
            .height(270.dp)
            .semantics(true) {},
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = stringResource(id = partnerCategory.getStringResource()),
                style =
                    defaultTextStyle(
                        DistrictDesign.Size.Font.SUB_TITLE,
                        Color.White,
                        fontWeight = FontWeight.Bold,
                    ),
                modifier =
                    Modifier.padding(
                        horizontal = DistrictDesign.Padding.BIG,
                        vertical = DistrictDesign.Padding.MEDIUM,
                    ),
            )
            Pager(
                list = partners,
            ) { partner ->
                val organization = partner.name

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = DistrictDesign.Padding.BIG),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        onClick = {
                            startLinkIntent(partner.url, context)
                        },
                        modifier =
                            Modifier
                                .clip(DistrictDesign.ROUNDED_SHAPE)
                                .semantics {
                                    contentDescription = "$semanticOpenLink $organization"
                                    role = Role.Button
                                },
                    ) {
                        Box(contentAlignment = Alignment.TopEnd) {
                            LoadingImage(
                                url = partner.image,
                                modifier =
                                    Modifier
                                        .height(160.dp)
                                        .background(Color.White)
                                        .padding(DistrictDesign.Padding.SMALL)
                                        .defaultMinSize(minWidth = 100.dp),
                            )
                            Box(
                                modifier =
                                    Modifier
                                        .padding(
                                            top = DistrictDesign.Padding.SMALL,
                                            end = DistrictDesign.Padding.SMALL,
                                        ),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_link),
                                    contentDescription = "",
                                    modifier =
                                        Modifier
                                            .background(
                                                SECONDARY_COLOR,
                                                CircleShape,
                                            )
                                            .size(DistrictDesign.Size.Icon.MEDIUM),
                                    tint = Color.White,
                                )
                            }
                        }
                    }
                    Text(
                        text = partner.name,
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
