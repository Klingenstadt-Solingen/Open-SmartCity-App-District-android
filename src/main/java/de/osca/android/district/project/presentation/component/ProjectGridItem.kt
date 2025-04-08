package de.osca.android.district.project.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.text.font.FontWeight
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import de.osca.android.district.R
import de.osca.android.district.core.PRIMARY_COLOR
import de.osca.android.district.core.presentation.component.ProgressIndicator
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.project.data.model.Project

@Composable
fun ProjectGridItem(project: Project) {
    val semanticStatus = stringResource(id = R.string.status)
    val status =
        project.status?.let { stringResource(id = it.getStringResource()) }
            ?: stringResource(R.string.unknown_status)

    Column(
        modifier =
            Modifier
                .background(PRIMARY_COLOR)
                .clip(DistrictDesign.ROUNDED_SHAPE)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clearAndSetSemantics {
                    contentDescription = "${project.name}. $semanticStatus:$status"
                    role = Role.Button
                },
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(project.image)
                .crossfade(true)
                .build(),
            contentDescription = "", // Don't use this, semantics reads this and fucks the user up
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .clip(RectangleShape),
            loading = {
                Box(modifier = Modifier.aspectRatio(2f), contentAlignment = Alignment.Center) {
                    ProgressIndicator()
                }
            },
            error = {
                Box(modifier = Modifier.aspectRatio(2f), contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cog),
                        contentDescription = "cog_icon",
                        tint = Color.White,
                        modifier = Modifier.aspectRatio(2f),
                    )
                }
            },
        )
        Column(
            modifier =
                Modifier
                    .padding(
                        horizontal = DistrictDesign.Padding.MEDIUM,
                    ).padding(bottom = DistrictDesign.Padding.MEDIUM)
                    .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier.padding(top = DistrictDesign.Padding.SMALL),
                text = project.name,
                style =
                    defaultTextStyle(
                        fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                        fontColor = Color.White,
                        fontWeight = FontWeight.Bold,
                    ),
                maxLines = 3,
            )

            Text(
                text = status,
                style =
                    defaultTextStyle(
                        fontSize = DistrictDesign.Size.Font.SMALLER_TEXT,
                        fontColor = Color.White,
                        fontWeight = FontWeight.Bold,
                    ),
                maxLines = 1,
            )
        }
    }
}
