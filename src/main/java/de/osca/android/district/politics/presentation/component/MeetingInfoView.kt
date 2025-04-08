package de.osca.android.district.politics.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.politics.data.model.PoliticMeeting
import de.osca.android.essentials.utils.extensions.toTimeString
import kotlinx.datetime.toJavaLocalDateTime

@Composable
fun MeetingInfoView(meeting: PoliticMeeting){
    Row(horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT)) {
        Icon(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = "",
            modifier = Modifier
                .size(DistrictDesign.Size.Icon.BIG)
        )

        if (meeting.startDateTime != null && meeting.endDateTime != null) {
            Column(modifier = Modifier.padding(start = DistrictDesign.Spacing.DEFAULT), horizontalAlignment = Alignment.Start) {
                meeting.startDateTime?.toJavaLocalDateTime().toTimeString("HH:mm")?.let {Text("$it -",
                    fontSize = DistrictDesign.Size.Font.DEFAULT)}
                meeting.endDateTime?.toJavaLocalDateTime().toTimeString("HH:mm")?.let {
                    Text(
                        it,
                        fontSize = DistrictDesign.Size.Font.DEFAULT)
                }
            }
        }
    }
}
