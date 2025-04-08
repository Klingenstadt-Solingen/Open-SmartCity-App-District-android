package de.osca.android.district.pressrelease.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.core.presentation.component.HtmlText
import de.osca.android.district.core.presentation.component.LoadingImage
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.pressrelease.presentation.component.PressReleaseDetailHead
import de.osca.android.district.pressrelease.viewmodel.PressReleaseDetailViewModel

@Composable
fun PressReleaseDetailScreen(
    objectId: String,
    pressReleaseDetailViewModel: PressReleaseDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(objectId) {
        pressReleaseDetailViewModel.fetchPressRelease(objectId)
    }

    TopBarScaffold { paddingValues ->
        LoadingWrapper(loadingState = pressReleaseDetailViewModel.loadingState) { pressRelease ->
            LazyColumn(
                modifier =
                    Modifier
                        .padding(paddingValues)
                        .padding(
                            horizontal = DistrictDesign.Padding.BIGGER,
                        ),
                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
            ) {
                item {
                    PressReleaseDetailHead(pressRelease)
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG)) {
                        if (!pressRelease.imageUrl.isNullOrBlank()) {
                            LoadingImage(
                                url = pressRelease.imageUrl!!,
                                modifier =
                                    Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .height(160.dp)
                                        // TODO: For accessibility it would be required to give a description of the image via the api
                                        .clip(DistrictDesign.ROUNDED_SHAPE)
                                        .clearAndSetSemantics {
                                            contentDescription = ""
                                            role = Role.Image
                                        },
                                contentScale = ContentScale.FillHeight,
                            )
                        }
                        HtmlText(
                            htmlString = pressRelease.content,
                        )
                    }
                }
            }
        }
    }
}
