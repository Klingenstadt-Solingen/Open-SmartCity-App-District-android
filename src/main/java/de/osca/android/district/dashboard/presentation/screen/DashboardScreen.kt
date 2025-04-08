package de.osca.android.district.dashboard.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.presentation.component.DistrictToolbar
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.dashboard.presentation.component.DashboardImage
import de.osca.android.district.dashboard.presentation.component.DashboardTileGrid
import de.osca.android.district.dashboard.presentation.component.DashboardWidgetList
import de.osca.android.district.dashboard.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel,
    districtViewModel: DistrictViewModel,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val topBarTextColor =
        remember {
            derivedStateOf {
                if (scrollBehavior.state.heightOffset.dp < (-65).dp) {
                    Color.Black
                } else {
                    Color.White
                }
            }
        }
    val districtState = districtViewModel.districtState
    val title =
        if (districtState is DistrictState.DISTRICT) {
            stringResource(
                id = R.string.district_prefix,
                districtState.district.name,
            )
        } else {
            stringResource(id = R.string.dashboard_city_name)
        }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color.White,
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            (
                MediumTopAppBar(
                    expandedHeight = 95.dp,
                    scrollBehavior = scrollBehavior,
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.White,
                            titleContentColor = topBarTextColor.value,
                        ),
                    title = {
                        Text(
                            title,
                            style =
                                TextStyle(
                                    fontSize = DistrictDesign.Size.Font.HEADLINE,
                                    fontWeight = FontWeight.Bold,
                                ),
                        )
                    },
                    actions = {
                        DistrictToolbar()
                    },
                )
            )
        },
    ) { padding ->
        Box(
            Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        ) {
            DashboardImage()
            Box(
                modifier =
                    Modifier
                        .padding(padding)
                        .offset(y = 50.dp)
                        .fillMaxSize()
                        .background(Color.White, DistrictDesign.ROUNDED_SHAPE),
            )
            Column(
                modifier =
                    Modifier
                        .padding(padding),
            ) {
                DashboardWidgetList(
                    widgets = dashboardViewModel.widgets,
                )
                DashboardTileGrid(
                    tiles = dashboardViewModel.tiles,
                )
            }
        }
    }
}
