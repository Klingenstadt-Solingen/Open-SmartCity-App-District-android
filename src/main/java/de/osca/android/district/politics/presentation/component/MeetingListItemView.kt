package de.osca.android.district.politics.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.disabledColor
import de.osca.android.district.politics.data.model.PoliticFile
import de.osca.android.district.politics.data.model.PoliticMeeting
import de.osca.android.district.politics.data.model.PoliticMeetingState
import de.osca.android.district.politics.navigation.PoliticsNavItems
import de.osca.android.essentials.utils.extensions.toTimeString
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun MeetingListItemView(meeting: PoliticMeeting, active: Boolean){
    val modifier = if(!active) Modifier.background(color = Color.disabledColor) else Modifier
    NavigationLink(
        navigate = PoliticsNavItems.PoliticsMeetingDetailNavItem(
            Json.encodeToString(meeting)),
        enabled = active,

    ) {
        Column(modifier = modifier.fillMaxWidth().padding(DistrictDesign.Padding.MEDIUM).padding(start = DistrictDesign.Spacing.DEFAULT), horizontalAlignment = Alignment.Start) {
            if(meeting.startDateTime != null) {
                meeting.startDateTime!!.toJavaLocalDateTime().toTimeString("dd. MMMM yyyy")
                    ?.let { Text(it, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT) }
            }

            Text(meeting.name, fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE, modifier = Modifier.padding(top = DistrictDesign.Padding.MEDIUM))
            MeetingInfoView(meeting= meeting)
        }
    }
}


@Preview
@Composable
fun MeetinListItemViewPreview(){
    val meeting = PoliticMeeting(
        id= "",
        name= "Test",
        startDateTime= LocalDateTime(0,1,1,0,0,0,0),
        endDateTime= LocalDateTime(0,1,1,0,0,0,0),
        location= null,
        meetingState= PoliticMeetingState.SCHEDULED,
        invitationFile= PoliticFile(
            id= "",
            mimeType= "Test invitation file",
            accessUrl= "",
            downloadUrl= "",
            name= "",
            fileName= "",
            text= "",
            date= null,
            size= 0,
            sha512Checksum= "",
            webUrl= "",
            updatedAt= LocalDateTime(0,1,1,0,0,0,0),
            createdAt= LocalDateTime(0,1,1,0,0,0,0),
        ),
        resultsProtocolFile= PoliticFile(
            id= "",
            mimeType= "Test result file",
            accessUrl= "",
            downloadUrl= "",
            name= "",
            fileName= "",
            text= "",
            date= null,
            size= 0,
            sha512Checksum= "",
            webUrl= "",
            updatedAt= LocalDateTime(0,1,1,0,0,0,0),
            createdAt= LocalDateTime(0,1,1,0,0,0,0),
        ),
        verbatimProtocolFile= PoliticFile(
            id= "",
            mimeType= "Test verbatim file",
            accessUrl= "",
            downloadUrl= "",
            name= "",
            fileName= "",
            text= "",
            date= null,
            size= 0,
            sha512Checksum= "",
            webUrl= "",
            updatedAt= LocalDateTime(0,1,1,0,0,0,0),
            createdAt= LocalDateTime(0,1,1,0,0,0,0),
        ),
        webUrl= "",
        updatedAt= LocalDateTime(0,1,1,0,0,0,0),
        createdAt= LocalDateTime(0,1,1,0,0,0,0),
    )
    MeetingListItemView(meeting= meeting, true)
}

