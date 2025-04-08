package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.osca.android.district.core.navigation.LocalNavigationController
import de.osca.android.district.core.presentation.design.DistrictDesign

@Composable
fun TopBarScaffold(
    floatingActionButton: @Composable () -> (Unit) = {},
    content: @Composable (paddingValues: PaddingValues) -> (Unit),
) {
    Scaffold(
        topBar = {
            DistrictTopBar()
        },
        containerColor = Color.White,
        floatingActionButton = floatingActionButton,
    ) {
        content(it)
    }
}

@Composable
fun TopBarScaffold(
    searchText: String,
    setSearchText: (String) -> (Unit),
    content: @Composable () -> (Unit),
) {
    TopBarScaffold { paddingValues ->
        Column(
            Modifier.padding(paddingValues),
        ) {
            Box(
                modifier =
                    Modifier
                        .padding(
                            start = DistrictDesign.Padding.BIG,
                            end = DistrictDesign.Padding.BIG,
                            bottom = DistrictDesign.Padding.BIGGER,
                        ),
            ) {
                SearchRow(searchText = searchText, setSearchText = setSearchText)
            }
            content()
        }
    }
}
