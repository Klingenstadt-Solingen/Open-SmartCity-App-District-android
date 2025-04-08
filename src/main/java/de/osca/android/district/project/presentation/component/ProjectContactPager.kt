package de.osca.android.district.project.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.LinkText
import de.osca.android.district.core.presentation.component.LoadingImage
import de.osca.android.district.core.presentation.component.pager.Pager
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.project.data.model.ProjectContact

@Composable
fun ProjectContactPager(contacts: List<ProjectContact>) {
    val semanticProfile = stringResource(id = R.string.profile_image)

    val localizedPhoneNumber = stringResource(id = R.string.phone_number)
    val localizedEmail = stringResource(id = R.string.email_address)
    val semanticOpenCall = stringResource(id = R.string.open_call)
    val semanticOpenMail = stringResource(id = R.string.open_email)
    val semanticOrganization = stringResource(id = R.string.organisation)

    Column(Modifier.height(270.dp).semantics(true, properties = {})) {
        Text(
            text = stringResource(id = R.string.project_contacts),
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
        Pager(list = contacts) {
            Column(
                modifier = Modifier.padding(horizontal = DistrictDesign.Padding.BIG).fillMaxWidth(),
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM)) {
                    LoadingImage(
                        url = it.image,
                        Modifier
                            .size(150.dp)
                            .clip(DistrictDesign.ROUNDED_SHAPE).clearAndSetSemantics {
                                contentDescription = semanticProfile
                                role = Role.Image
                            },
                        default = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_district_person),
                                contentDescription = "",
                            )
                        },
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM)) {
                        Text(
                            text = it.name,
                            style =
                                defaultTextStyle(
                                    DistrictDesign.Size.Font.SUB_SUB_TITLE,
                                    Color.White,
                                    fontWeight = FontWeight.Bold,
                                ),
                            modifier = Modifier.padding(bottom = DistrictDesign.Padding.MEDIUM),
                            maxLines = 1,
                        )

                        Titled(
                            title = localizedPhoneNumber,
                            modifier =
                                Modifier.semantics(mergeDescendants = true) {
                                    // TODO: make PhonNumber readable (remove symbols like "-" and add a space char between every symbol)
                                    contentDescription = "$localizedPhoneNumber ${it.phoneNumber}"
                                },
                        ) {
                            LinkText(
                                text = it.phoneNumber,
                                style =
                                    defaultTextStyle(
                                        DistrictDesign.Size.Font.SMALL_TEXT,
                                        Color.White,
                                    ),
                                modifier =
                                    Modifier.semantics {
                                        contentDescription = semanticOpenCall
                                    },
                            )
                        }

                        Titled(
                            title = localizedEmail,
                            modifier =
                                Modifier.semantics(mergeDescendants = true) {
                                    contentDescription = "$localizedEmail ${it.email}"
                                },
                        ) {
                            LinkText(
                                text = it.email,
                                style =
                                    defaultTextStyle(
                                        DistrictDesign.Size.Font.SMALL_TEXT,
                                        Color.White,
                                    ),
                                modifier =
                                    Modifier.semantics {
                                        contentDescription = semanticOpenMail
                                    },
                            )
                        }
                    }
                }
                Text(
                    text = it.organization,
                    style = defaultTextStyle(DistrictDesign.Size.Font.NORMAL_TEXT, Color.White),
                    maxLines = 2,
                    modifier =
                        Modifier.padding(top = DistrictDesign.Padding.SMALL).semantics {
                            contentDescription = "$semanticOrganization: ${it.organization}"
                        },
                )
            }
        }
    }
}

@Composable
fun Titled(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
    ) {
        Text(
            text = title,
            style =
                defaultTextStyle(
                    DistrictDesign.Size.Font.NORMAL_TEXT,
                    Color.White,
                    fontWeight = FontWeight.Bold,
                ),
        )
        content()
    }
}
