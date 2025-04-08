package de.osca.android.district.politics.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import de.osca.android.district.R
import de.osca.android.district.core.paging.PagingRefreshEffect
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.core.presentation.component.pager.applyPagingHandler
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.politics.navigation.PoliticsNavItems
import de.osca.android.district.politics.presentation.component.MemberListItemView
import de.osca.android.district.politics.viewmodel.PoliticMemberListViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun PoliticMemberListScreen(
    politicMemberListViewModel: PoliticMemberListViewModel = hiltViewModel(),
    organizationId: String,
    organizationTitle: String
) {
    politicMemberListViewModel.updateOrganizationId(organizationId)

    val scrollState = rememberLazyListState()

    val members = politicMemberListViewModel.members.collectAsLazyPagingItems()

    val isRefreshing = remember { mutableStateOf(false) }

    PagingRefreshEffect(members, isRefreshing)

    TopBarScaffold { innerPadding ->
        Column(
            Modifier.padding(innerPadding).padding(horizontal = DistrictDesign.Padding.BIGGER),
            verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT)
        ) {
            Text(
                text = organizationTitle,
                style =
                defaultTextStyle(
                    fontSize = DistrictDesign.Size.Font.SUB_TITLE,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Text(
                text = stringResource(id = R.string.politic_members),
                style =
                defaultTextStyle(
                    fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                    fontWeight = FontWeight.Bold,
                ),
            )

            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
                contentPadding = PaddingValues(vertical = DistrictDesign.Padding.MEDIUM),
            ) {
                items(members.itemCount) { index ->
                    members[index]?.let { member ->
                        NavigationLink(
                            navigate = PoliticsNavItems.PoliticsMemberDetailNavItem(Json.encodeToString(member)),
                        ) {
                            MemberListItemView(member)
                        }
                    }
                }
                applyPagingHandler(members)
            }
        }
    }
}

@Preview
@Composable
fun PoliticMemberListScreenPreview() {
    PoliticDistrictListScreen()
}
