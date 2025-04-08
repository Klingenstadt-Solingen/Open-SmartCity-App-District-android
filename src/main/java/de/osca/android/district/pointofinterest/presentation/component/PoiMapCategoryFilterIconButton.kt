package de.osca.android.district.pointofinterest.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.accentColor
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.pointofinterest.data.model.PoiCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoiMapCategoryFilterIconButton(
    modifier: Modifier = Modifier,
    categories: List<PoiCategory>,
    selectedCategories: Set<String>,
    onClickCategory: ((category: PoiCategory, selected: Boolean) -> Unit)?,
) {
    var showCategories by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    FloatingActionButton(
        shape = CircleShape,
        containerColor = Color.accentColor,
        modifier = modifier.clip(CircleShape),
        onClick = {
            showCategories = true
        },
    ) {
        Icon(
            modifier =
                Modifier
                    .size(DistrictDesign.Size.Icon.HUGE,)
                    .padding(DistrictDesign.Padding.MEDIUM),
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "Filter",
            tint = Color.primary,
        )

        if (showCategories) {
            ModalBottomSheet(
                onDismissRequest = {
                    showCategories = false
                },
                sheetState = bottomSheetState,
            ) {
                PoiCategoryGrid(
                    categories = categories,
                    selectedCategories = selectedCategories,
                    onClickCategory = onClickCategory,
                )
            }
        }
    }
}
