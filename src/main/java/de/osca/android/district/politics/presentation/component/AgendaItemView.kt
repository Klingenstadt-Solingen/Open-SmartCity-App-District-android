package de.osca.android.district.politics.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.politics.data.model.PoliticAgendaItem
import kotlinx.datetime.LocalDateTime

@Composable
fun AgendaItemView(agendaItem: PoliticAgendaItem) {
        Row(
            modifier = Modifier.padding(vertical = DistrictDesign.Padding.SMALL),
            horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT)
        ) {
            if (agendaItem.number != null) {
                Text(agendaItem.number!!, fontSize = DistrictDesign.Size.Font.DEFAULT)
            }
            Text(agendaItem.name, fontSize = DistrictDesign.Size.Font.DEFAULT)
        }
}

@Composable
@Preview
fun AgendaItemViewPreview(){
     AgendaItemView(agendaItem = PoliticAgendaItem(
        id= "String",
        name= "String",
        order= 0,
        number= "String?",
        startDateTime= null,
        endDateTime= null,
        public= true,
        result= "String?",
        resolutionText= "String?",
        resolutionFile= null,
        consultationPaper= null,
        auxiliaryFiles=  arrayListOf(),
        webUrl= "String?",
        updatedAt= LocalDateTime(0,1,1,0,0,0,0),
        createdAt= LocalDateTime(0,1,1,0,0,0,0),
     ))
}

