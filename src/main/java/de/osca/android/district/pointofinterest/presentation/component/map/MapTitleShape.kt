package de.osca.android.district.pointofinterest.presentation.component.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.ModuleTitle

class MapTitleShape(private val cornerSize: Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val radius = cornerSize.value * 2
        // agony
        val path =
            Path().apply {
                moveTo(0f, 0f)
                arcTo(
                    Rect(
                        size.width - 2 * radius,
                        0f,
                        size.width + radius,
                        3 * radius,
                    ),
                    startAngleDegrees = -90f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false,
                )
                arcTo(
                    Rect(
                        size.width - 5 * radius,
                        size.height - 3 * radius,
                        size.width - 2 * radius,
                        size.height,
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false,
                )
                lineTo(0f, size.height)
                close()
            }
        return Outline.Generic(path)
    }
}

@Preview
@Composable
private fun MapTitleShapePreview() {
    ModuleTitle(
        title = R.string.poi_module_title,
        modifier =
            Modifier
                .background(Color.White, MapTitleShape(10.dp))
                .padding(top = 10.dp, start = 15.dp, end = 30.dp, bottom = 10.dp),
    )
}
