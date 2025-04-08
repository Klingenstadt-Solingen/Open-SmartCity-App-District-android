package de.osca.android.district.pointofinterest.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.ProgressIndicator
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.pointofinterest.data.model.PoiCategory
import de.osca.android.district.pointofinterest.data.model.getConvertedDetails
import de.osca.android.district.pointofinterest.presentation.component.PoiDetailListItem
import de.osca.android.district.pointofinterest.viewmodel.PoiDetailViewModel

/**
 * Corresponds to PoiDetailView in Xcode
 */
@Composable
fun PoiDetailScreen(
    modifier: Modifier = Modifier,
    poiObjectId: String,
    poiCategory: PoiCategory?,
    poiDetailViewModel: PoiDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(poiObjectId) {
        poiDetailViewModel.requestPoiById(poiObjectId)
    }

    val scrollState = rememberScrollState()

    LoadingWrapper(
        poiDetailViewModel.loadingState,
        replacement = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ProgressIndicator()
            }
        },
    ) { pointOfInterest ->
        Column(
            modifier
                .verticalScroll(scrollState)
                .padding(horizontal = DistrictDesign.Padding.BIGGER),
            verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = poiCategory?.getIconImageUrl() ?: "",
                    contentDescription = poiCategory?.name ?: "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(DistrictDesign.Size.Icon.BIGGER),
                )
                Text(
                    text = pointOfInterest.name,
                    fontSize = DistrictDesign.Size.Font.SUB_TITLE,
                    fontWeight = FontWeight.Bold,
                )
            }

            val iterator = getConvertedDetails(pointOfInterest).iterator()

            while (iterator.hasNext()) {
                PoiDetailListItem(iterator.next())

                if (iterator.hasNext()) HorizontalDivider()
            }
        }
    }
}
