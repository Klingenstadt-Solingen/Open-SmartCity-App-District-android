package de.osca.android.district.core.presentation.design

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object DistrictDesign {
    object Padding {
        val SMALLEST = 2.dp
        val SMALL = 5.dp
        val DEFAULT = 8.dp
        val MEDIUM = 10.dp
        val BIG = 15.dp
        val BIGGER = 18.dp
        val HUGE = 20.dp
    }

    object Spacing {
        val DEFAULT = 8.dp
        val SMALL = 2.dp
        val MEDIUM = 5.dp
        val BIG = 10.dp
        val BIGGER = 15.dp
        val HUGE = 25.dp
    }

    val GRID_CELLS_ADAPTIVE = GridCells.Adaptive(150.dp)

    val ROUNDED_SHAPE_SMALL = RoundedCornerShape(5.dp)
    val ROUNDED_SHAPE = RoundedCornerShape(10.dp)
    val ELEVATION_SMALL = 5.dp
    val ELEVATION_MEDIUM = 8.dp
    object Thickness {
        val DEFAULT = 2.dp
    }
    object Size {
        object Icon {
            val SMALL = 10.dp
            val MEDIUM = 15.dp
            val BIG = 25.dp
            val BIGGER = 45.dp
            val HUGE = 60.dp
        }

        object Font {
            val HEADLINE = 24.sp

            val SUB_TITLE = 22.sp

            val SUB_SUB_TITLE = 18.sp

            val NORMAL_TEXT = 14.sp

            val SMALL_TEXT = 12.sp
            val SMALLER_TEXT = 10.sp
            val DEFAULT = 16.sp
        }
    }
}
