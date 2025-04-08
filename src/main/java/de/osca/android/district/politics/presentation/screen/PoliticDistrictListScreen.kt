package de.osca.android.district.politics.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.ModuleTitle
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.politics.navigation.PoliticsNavItems
import de.osca.android.district.politics.viewmodel.PoliticDistrictListViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun PoliticDistrictListScreen(politicDistrictListViewModel: PoliticDistrictListViewModel = hiltViewModel()) {
    val scrollState = rememberLazyListState()
    LoadingWrapper(loadingState = politicDistrictListViewModel.loadingState) { districts ->
        TopBarScaffold { innerPadding ->
            Column(
                Modifier.padding(innerPadding),
            ) {
                ModuleTitle(title = R.string.politics_module_title)

                LazyColumn(
                    state = scrollState,
                    modifier =
                        Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
                    verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
                    contentPadding = PaddingValues(vertical = DistrictDesign.Padding.MEDIUM),
                ) {
                    items(districts.count()) { index ->
                        districts[index].let { district ->
                            NavigationLink(
                                navigate =
                                    PoliticsNavItems.PoliticsDetailsNavItem(
                                        district.objectId,
                                        district.logo.url,
                                        Json.encodeToString(district.serialArea),
                                    ),
                            ) {
                                Text(
                                    district.name,
                                    Modifier.padding(DistrictDesign.Padding.HUGE).fillMaxWidth(),
                                    fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PoliticDistrictListScreenPreview() {
    PoliticDistrictListScreen()
}
