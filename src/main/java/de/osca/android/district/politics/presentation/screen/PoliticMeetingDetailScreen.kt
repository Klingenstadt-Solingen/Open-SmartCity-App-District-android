package de.osca.android.district.politics.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import de.osca.android.district.R
import de.osca.android.district.core.paging.PagingRefreshEffect
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.component.pager.applyPagingHandler
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.politics.data.model.PoliticFile
import de.osca.android.district.politics.data.model.PoliticMeeting
import de.osca.android.district.politics.presentation.component.AgendaItemView
import de.osca.android.district.politics.presentation.component.MeetingFileItemView
import de.osca.android.district.politics.presentation.component.MeetingInfoView
import de.osca.android.district.politics.viewmodel.PoliticMeetingDetailViewModel
import de.osca.android.essentials.utils.extensions.toTimeString
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime

@Composable
fun PoliticMeetingDetailScreen(
    politicMeetingDetailViewModel: PoliticMeetingDetailViewModel = hiltViewModel(),
    meeting: PoliticMeeting,
) {
    politicMeetingDetailViewModel.updateMeetingId(meeting.id)

    val scrollState = rememberLazyListState()

    val agendaItems = politicMeetingDetailViewModel.agendaItems.collectAsLazyPagingItems()

    val isRefreshing = remember { mutableStateOf(false) }

    PagingRefreshEffect(agendaItems, isRefreshing)

    TopBarScaffold { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = DistrictDesign.Spacing.BIGGER),
            verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT)
        ) {
            meeting.startDateTime?.let {
                it.toJavaLocalDateTime().toTimeString("dd. MMMM yyyy")
                    ?.let { it1 -> Text(it1, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT) }
            }

            Text(
                meeting.name,
                fontSize = DistrictDesign.Size.Font.SUB_TITLE,
                fontWeight = FontWeight.Bold,
            )
            MeetingInfoView(meeting)

            if (meeting.resultsProtocolFile?.name != null &&
                meeting.resultsProtocolFile?.downloadUrl != null &&
                meeting.resultsProtocolFile?.mimeType != null
            ) {
                MeetingFileItemView(
                    meeting.resultsProtocolFile?.downloadUrl!!,
                    meeting.resultsProtocolFile?.name!!,
                    meeting.resultsProtocolFile?.mimeType!!,
                )
            }
            if (meeting.invitationFile?.downloadUrl != null &&
                meeting.invitationFile?.name != null &&
                meeting.invitationFile?.mimeType != null
            ) {
                MeetingFileItemView(
                    meeting.invitationFile?.downloadUrl!!,
                    meeting.invitationFile?.name!!,
                    meeting.invitationFile?.mimeType!!,
                )
            }
            if (meeting.verbatimProtocolFile?.downloadUrl != null &&
                meeting.verbatimProtocolFile?.name != null &&
                meeting.verbatimProtocolFile?.mimeType != null
            ) {
                MeetingFileItemView(
                    meeting.verbatimProtocolFile?.downloadUrl!!,
                    meeting.verbatimProtocolFile?.name!!,
                    meeting.verbatimProtocolFile?.mimeType!!,
                )
            }

            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.SMALL),
            ) {
                items(agendaItems.itemCount) { index ->
                    agendaItems[index]?.let { agendaItem ->
                        DisclosureGroup(
                            modifier = Modifier.padding(vertical = DistrictDesign.Padding.SMALL),
                            { AgendaItemView(agendaItem) },
                            (
                                agendaItem.consultationPaper?.mainFile?.downloadUrl != null &&
                                    agendaItem.consultationPaper?.mainFile?.name != null &&
                                    agendaItem.consultationPaper?.mainFile?.mimeType != null
                            ),
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.SMALL)) {
                                Row(modifier = Modifier.padding(vertical = DistrictDesign.Padding.MEDIUM)) {
                                    MeetingFileItemView(
                                        agendaItem.consultationPaper?.mainFile?.downloadUrl!!,
                                        agendaItem.consultationPaper?.mainFile?.name!!,
                                        agendaItem.consultationPaper?.mainFile?.mimeType!!,
                                    )
                                }

                                if (agendaItem.resolutionFile?.downloadUrl != null &&
                                    agendaItem.resolutionFile?.name != null &&
                                    agendaItem.resolutionFile?.mimeType != null
                                ) {
                                    Row(modifier = Modifier.padding(vertical = DistrictDesign.Padding.MEDIUM)) {
                                        MeetingFileItemView(
                                            agendaItem.resolutionFile?.downloadUrl!!,
                                            agendaItem.resolutionFile?.name!!,
                                            agendaItem.resolutionFile?.mimeType!!,
                                        )
                                    }
                                }
                                Column(verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.SMALL)) {
                                    agendaItem.auxiliaryFiles.forEach { auxiliaryFile ->
                                        if (auxiliaryFile.downloadUrl != null &&
                                            auxiliaryFile.name != null &&
                                            auxiliaryFile.mimeType != null
                                        ) {
                                            Row(modifier = Modifier.padding(vertical = DistrictDesign.Padding.MEDIUM)) {
                                                MeetingFileItemView(
                                                    auxiliaryFile.downloadUrl!!,
                                                    auxiliaryFile.name!!,
                                                    auxiliaryFile.mimeType!!,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                applyPagingHandler(agendaItems)
            }
        }
    }
}

@Preview
@Composable
fun PoliticMeetingDetailScreenPreview() {
    val meeting =
        PoliticMeeting(
            name = "Test",
            startDateTime = LocalDateTime(2024, 1, 1, 0, 0),
            endDateTime = LocalDateTime(2024, 1, 1, 0, 0),
            resultsProtocolFile =
                PoliticFile(
                    name = "Test File",
                    mimeType = "",
                    updatedAt = LocalDateTime(2024, 1, 1, 0, 0),
                    accessUrl = "",
                    downloadUrl = "",
                ),
        )
    return PoliticMeetingDetailScreen(meeting = meeting)
}

@Composable
fun DisclosureGroup(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    clickable: Boolean,
    content: @Composable () -> (Unit),
) {
    val clickState = remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        clickState.value = clickState.value.not()
                    },
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.fillMaxWidth(if (clickable) 0.8f else 1f)) {
                title()
            }
            Column {
                if (clickable) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_right),
                        contentDescription = "",
                        modifier =
                            Modifier
                                .size(
                                    DistrictDesign.Size.Icon.MEDIUM,
                                )
                                .rotate(if (clickState.value) 90.0F else 0.0F),
                    )
                }
            }
        }
        if (clickable && clickState.value) {
            Row {
                content()
            }
        }
    }
}
