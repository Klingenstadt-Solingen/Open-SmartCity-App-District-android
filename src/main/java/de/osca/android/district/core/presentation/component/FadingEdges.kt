package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun FadingEdges(edges: Dp) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier =
                Modifier.width(edges).fillMaxHeight().background(
                    brush =
                        Brush.horizontalGradient(
                            colors =
                                listOf(
                                    Color.White,
                                    Color.White,
                                    Color.Transparent,
                                ),
                        ),
                ),
        ) {}
        Box(
            modifier =
                Modifier.width(edges).fillMaxHeight().background(
                    brush =
                        Brush.horizontalGradient(
                            colors =
                                listOf(
                                    Color.Transparent,
                                    Color.White,
                                    Color.White,
                                ),
                        ),
                ),
        ) {}
    }
}
