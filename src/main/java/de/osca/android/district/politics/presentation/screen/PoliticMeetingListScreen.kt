package de.osca.android.district.politics.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import de.osca.android.district.R
import de.osca.android.district.core.paging.PagingRefreshEffect
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.component.pager.applyPagingHandler
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.politics.presentation.component.MeetingListItemView
import de.osca.android.district.politics.viewmodel.PoliticMeetingListViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun PoliticMeetingListScreen(
    politicMeetingListViewModel: PoliticMeetingListViewModel = hiltViewModel(),
    organizationId: String,
    organizationTitle: String
) {
    politicMeetingListViewModel.updateOrganizationId(organizationId)

    val future by politicMeetingListViewModel.future.collectAsState()

    val scrollState = rememberLazyListState()

    val meetings = politicMeetingListViewModel.meetings.collectAsLazyPagingItems()

    val isRefreshing = remember { mutableStateOf(false) }

    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    PagingRefreshEffect(meetings, isRefreshing)

    TopBarScaffold { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = DistrictDesign.Padding.BIGGER),
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
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.politic_meetings),
                    style =
                    defaultTextStyle(
                        fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE,
                        fontWeight = FontWeight.Bold,
                    ),
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(id = R.string.politic_in_future),
                            style =
                            defaultTextStyle(
                            fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE,
                    ))
                    Switch(
                        colors= SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color.Green,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor= Color.LightGray,
                        ),
                        checked = future,
                        onCheckedChange = {
                            politicMeetingListViewModel.updateFuture(it)
                        },
                        modifier = Modifier.padding(start = DistrictDesign.Spacing.DEFAULT)
                    )
                }
            }

            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
                contentPadding = PaddingValues(vertical = DistrictDesign.Padding.MEDIUM),
            ) {
                items(meetings.itemCount) { index ->
                    meetings[index]?.let { meeting ->
                        MeetingListItemView(meeting, (meeting.startDateTime ?: now) < now)
                    }
                }
                applyPagingHandler(meetings)
            }
        }
    }
}

@Preview
@Composable
fun PoliticMeetingListScreenPreview() {
    PoliticDistrictListScreen()
}
