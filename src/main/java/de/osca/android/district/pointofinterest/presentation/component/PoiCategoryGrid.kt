package de.osca.android.district.pointofinterest.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.pointofinterest.data.model.PoiCategory

/**
 * Corresponds to PoiCategoryGridView in Xcode
 */
@Composable
fun PoiCategoryGrid(
    modifier: Modifier = Modifier,
    onClickCategory: ((category: PoiCategory, selected: Boolean) -> Unit)?,
    categories: List<PoiCategory>,
    selectedCategories: Set<String>,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
        contentPadding = PaddingValues(DistrictDesign.Padding.BIGGER),
    ) {
        items(categories) { cat ->
            val isSelected = selectedCategories.contains(cat.sourceId)
            PoiCategoryItemButton(
                category = cat,
                checked = isSelected,
                onCheckedChange =
                    if (onClickCategory != null) {
                        { sel ->
                            onClickCategory(cat, sel)
                        }
                    } else {
                        null
                    },
            )
        }
    }
}
