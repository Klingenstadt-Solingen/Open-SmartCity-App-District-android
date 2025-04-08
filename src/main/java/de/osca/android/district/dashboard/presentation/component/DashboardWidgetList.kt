package de.osca.android.district.dashboard.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.osca.android.district.core.navigation.LocalNavigationController
import de.osca.android.district.core.presentation.component.DashboardWidgetWrapper
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.modifier.innerShadow

@Composable
fun DashboardWidgetList(
    widgets: List<DashboardWidgetWrapper>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues =
        PaddingValues(
            horizontal = DistrictDesign.Padding.BIG,
            vertical = DistrictDesign.Padding.MEDIUM,
        ),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
    ) {
        itemsIndexed(widgets) { index, widget ->
            Box(
                modifier =
                    if (index == 0) {
                        Modifier
                            .innerShadow(
                                shape = DistrictDesign.ROUNDED_SHAPE,
                                color = Color.Black.copy(alpha = 0.35f),
                                spread = 0.dp,
                                blur = 3.dp,
                                offsetX = 0.dp,
                                offsetY = 4.dp,
                            ).background(Color.White, shape = DistrictDesign.ROUNDED_SHAPE)
                    } else {
                        Modifier
                    },
            ) {
                widget.ContentView(LocalNavigationController.current)
            }
        }
    }
}
