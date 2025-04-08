package de.osca.android.district.pressrelease.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.pressrelease.data.model.PressRelease
import de.osca.android.district.pressrelease.navigation.PressReleaseNavItems

@Composable
fun PressReleaseWidgetItem(pressRelease: PressRelease) {
    NavigationLink(
        navigate = PressReleaseNavItems.PressReleaseDetailNavItem(pressRelease.objectId),
        modifier = Modifier.fillMaxSize(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(all = DistrictDesign.Padding.MEDIUM),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_right),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .height(DistrictDesign.Size.Icon.SMALL)
                            .alignBy(FirstBaseline)
                            .offset(y = 5.dp),
                )
                // takes upp all width when going multiline TODO: custom component
                Text(
                    text = pressRelease.title,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PressReleaseWidgetItemPreview() {
    PressReleaseWidgetItem(pressRelease = PressRelease.dummy())
}
