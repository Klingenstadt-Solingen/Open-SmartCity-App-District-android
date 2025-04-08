package de.osca.android.district.project.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.R
import de.osca.android.district.core.PRIMARY_COLOR
import de.osca.android.district.core.presentation.component.LoadingImage
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.project.presentation.component.ProjectContactPager
import de.osca.android.district.project.presentation.component.ProjectDetailHead
import de.osca.android.district.project.presentation.component.ProjectPartnerPager
import de.osca.android.district.project.viewmodel.ProjectDetailViewModel
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
fun ProjectDetailScreen(
    viewModel: ProjectDetailViewModel = hiltViewModel(),
    objectId: String,
) {
    LaunchedEffect(objectId) {
        viewModel.requestProject(objectId)
    }

    TopBarScaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LoadingWrapper(
                loadingState = viewModel.loadingState,
                retry = { viewModel.requestProject(objectId) },
            ) {
                LazyColumn {
                    item {
                        Column(
                            modifier =
                                Modifier.padding(horizontal = DistrictDesign.Padding.BIG),
                        ) {
                            ProjectDetailHead(project = it)
                            Text(
                                text = it.summary,
                                style = defaultTextStyle(DistrictDesign.Size.Font.NORMAL_TEXT),
                                modifier = Modifier.padding(vertical = DistrictDesign.Padding.SMALL),
                            )
                            if (it.image.isNotBlank()) {
                                LoadingImage(
                                    url = it.image,
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
                            it.volume?.let { volume ->
                                if (volume > 0) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = DistrictDesign.Padding.SMALL),
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_euro_sign),
                                            contentDescription = "",
                                            modifier = Modifier.size(DistrictDesign.Size.Icon.BIG),
                                            tint = PRIMARY_COLOR,
                                        )
                                        val nf = NumberFormat.getCurrencyInstance(Locale.GERMANY)
                                        nf.currency = Currency.getInstance("EUR")
                                        Text(
                                            text = nf.format(volume),
                                            modifier = Modifier.padding(start = DistrictDesign.Padding.MEDIUM),
                                            style = defaultTextStyle(DistrictDesign.Size.Font.NORMAL_TEXT),
                                        )
                                    }
                                }
                            }
                            Text(
                                text = it.content,
                                style = defaultTextStyle(DistrictDesign.Size.Font.SMALL_TEXT),
                                modifier =
                                    Modifier.padding(
                                        top = DistrictDesign.Padding.SMALL,
                                        bottom = DistrictDesign.Padding.BIG,
                                    ),
                            )
                        }
                    }
                    item {
                        Column(
                            Modifier
                                .background(PRIMARY_COLOR)
                                .fillMaxWidth()
                                .padding(bottom = DistrictDesign.Padding.MEDIUM),
                        ) {
                            if (viewModel.contacts.isNotEmpty()) {
                                ProjectContactPager(viewModel.contacts)
                            }

                            viewModel.partnerMap.entries.map { entry ->
                                ProjectPartnerPager(
                                    partnerCategory = entry.key,
                                    partners = entry.value.toList(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
