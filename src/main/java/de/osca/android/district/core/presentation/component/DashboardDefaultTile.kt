package de.osca.android.district.core.presentation.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.primary

@Composable
fun DashboardDefaultTile(
    modifier: Modifier = Modifier,
    @StringRes
    title: Int,
    @DrawableRes
    image: Int,
    count: Int? = 0,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .padding(DistrictDesign.Padding.MEDIUM)
                .fillMaxSize(),
        contentAlignment = Alignment.TopStart,
    ) {
        if (count != null && count > 0) {
            CounterCircle(count)
        }

        Column(
            verticalArrangement =
                Arrangement.spacedBy(
                    DistrictDesign.Spacing.BIG,
                    Alignment.CenterVertically,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .padding(horizontal = DistrictDesign.Padding.MEDIUM)
                    .padding(top = DistrictDesign.Padding.SMALLEST)
                    .fillMaxSize(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement =
                    Arrangement.spacedBy(
                        DistrictDesign.Spacing.BIG,
                        Alignment.CenterHorizontally,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
            ) {
                Icon(
                    modifier = Modifier.size(DistrictDesign.Size.Icon.BIGGER),
                    painter = painterResource(image),
                    contentDescription = null,
                    tint = Color.primary,
                )
                content()
            }

            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                text = stringResource(title),
                fontSize = DistrictDesign.Size.Font.DEFAULT,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardDefaultTilePreview() {
    DashboardDefaultTile(
        title = R.string.error_message_no_result,
        image = R.drawable.ic_left,
        count = 18,
    )
}
