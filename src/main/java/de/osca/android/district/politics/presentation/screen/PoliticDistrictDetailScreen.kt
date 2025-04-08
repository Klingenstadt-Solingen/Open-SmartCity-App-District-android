package de.osca.android.district.politics.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.TileOverlay
import com.google.maps.android.compose.rememberCameraPositionState
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.ModuleTitle
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.util.getBoundsLatLong
import de.osca.android.district.pointofinterest.presentation.component.map.PoiTileOverlay
import de.osca.android.district.politics.navigation.PoliticsNavItems
import de.osca.android.district.politics.viewmodel.PoliticDistrictDetailViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PoliticDistrictDetailScreen(
    politicDistrictDetailViewModel: PoliticDistrictDetailViewModel = hiltViewModel(),
    districtId: String,
    logoUrl: String,
    area: List<Pair<Double, Double>>,
) {
    LaunchedEffect(Unit) {
        politicDistrictDetailViewModel.refreshOrganisation(districtId)
    }

    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()
    val areaLatLng = area.map { LatLng(it.first, it.second) }

    TopBarScaffold { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding),
        ) {
            ModuleTitle(title = R.string.politics_module_title)
            LazyColumn(
                state = LazyListState(),
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
            ) {
                item {
                    LoadingWrapper(loadingState = politicDistrictDetailViewModel.loadingState) { organization ->
                        organization?.let {
                            Column(
                                Modifier.padding(top = DistrictDesign.Padding.HUGE),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
                            ) {
                                Text(
                                    organization.name,
                                    fontSize = DistrictDesign.Size.Font.HEADLINE,
                                    fontWeight = FontWeight.Bold,
                                    modifier =
                                        Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
                                )

                                SvgImageSample(getHttps(logoUrl))

                                organization.newestMayor?.let { mayor ->
                                    Column(
                                        Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
                                    ) {
                                        mayor.role?.let {
                                            Text(
                                                it,
                                                fontSize = DistrictDesign.Size.Font.DEFAULT,
                                                fontWeight = FontWeight.Bold,
                                            )
                                        }
                                        Text(
                                            mayor.person.name(),
                                            fontSize = DistrictDesign.Size.Font.DEFAULT,
                                        )
                                    }
                                }

                                GoogleMap(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth().height(200.dp),
                                    cameraPositionState = cameraPositionState,
                                    uiSettings =
                                        MapUiSettings(
                                            compassEnabled = false,
                                            tiltGesturesEnabled = false,
                                            mapToolbarEnabled = false,
                                            indoorLevelPickerEnabled = false,
                                            myLocationButtonEnabled = false,
                                            zoomControlsEnabled = false,
                                            zoomGesturesEnabled = false,
                                            rotationGesturesEnabled = false,
                                            scrollGesturesEnabled = false,
                                            scrollGesturesEnabledDuringRotateOrZoom = false,
                                        ),
                                    onMapLoaded = {
                                    },
                                ) {
                                    TileOverlay(tileProvider = PoiTileOverlay())

                                    coroutineScope.launch {
                                        cameraPositionState.move(
                                            getBoundsLatLong(
                                                areaLatLng,
                                                cameraPositionState.position.target,
                                            ),
                                        )
                                    }

                                    Polyline(
                                        points = areaLatLng,
                                        color = Color(0xFF005AAA),
                                        zIndex = 10F,
                                    )
                                }
                                organization.location?.description?.let { description ->
                                    Text(
                                        description,
                                        fontSize = DistrictDesign.Size.Font.DEFAULT,
                                        modifier =
                                            Modifier
                                                .padding(horizontal = DistrictDesign.Padding.BIGGER),
                                        maxLines = 2,
                                        minLines = 2,
                                    )
                                }
                                Column(
                                    modifier = Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
                                    verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
                                ) {
                                    Text(
                                        "${organization.memberCount} ${stringResource(id = R.string.council_members)}",
                                        fontSize = DistrictDesign.Size.Font.DEFAULT,
                                    )
                                    Text(
                                        "${organization.votingMemberCount} ${stringResource(id = R.string.authorized_votes)}",
                                        fontSize = DistrictDesign.Size.Font.DEFAULT,
                                    )
                                }

                                GetNavigationLink(
                                    id = R.string.politic_meetings,
                                    route =
                                        PoliticsNavItems.PoliticsMeetingsNavItem(
                                            organization.id,
                                            organization.name,
                                        ),
                                )

                                GetNavigationLink(
                                    id = R.string.politic_members,
                                    route =
                                        PoliticsNavItems.PoliticsMembersNavItem(
                                            organization.id,
                                            organization.name,
                                        ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ADDS HTTPS TO URL UNTIL ISSUE IS RESOLVED WITH PARSE SERVER
fun getHttps(url: String): String {
    var https = url
    if (!url.startsWith("https")) {
        https = url.replace("http", "https")
    }
    return https
}

@Composable
fun <T : Any> GetNavigationLink(
    id: Int,
    route: T,
) {
    NavigationLink(
        modifier = Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
        navigate = route,
    ) {
        Row(
            Modifier.padding(DistrictDesign.Padding.MEDIUM)
                .padding(start = DistrictDesign.Padding.MEDIUM).fillMaxWidth(),
        ) {
            Text(stringResource(id = id), fontSize = DistrictDesign.Size.Font.DEFAULT)
            Spacer(Modifier.width(20.dp))
        }
    }
}

@Preview
@Composable
fun PoliticDistrictDetailScreenPreview() {
    // PoliticDistrictDetailScreen(district: District())
}

@Composable
fun SvgImageSample(url: String) {
    val painter =
        rememberAsyncImagePainter(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .decoderFactory(SvgDecoder.Factory())
                    .data(url)
                    .size(Size.ORIGINAL)
                    .build(),
        )
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.height(100.dp),
        contentScale = ContentScale.Fit,
    )
}
