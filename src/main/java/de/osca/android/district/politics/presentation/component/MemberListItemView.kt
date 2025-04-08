package de.osca.android.district.politics.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.politics.data.model.PoliticMembership
import de.osca.android.district.politics.data.model.PoliticPerson
import kotlinx.datetime.LocalDateTime

@Composable
fun MemberListItemView(membership: PoliticMembership) {
    Column (Modifier.fillMaxWidth().padding(DistrictDesign.Padding.MEDIUM)
        .padding(start = DistrictDesign.Padding.MEDIUM) , horizontalAlignment = Alignment.Start) {
        Text(membership.person.name() ?: "", fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE)
        Text(membership.role ?: "", fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
            modifier = Modifier.padding(top = DistrictDesign.Spacing.DEFAULT))
    }
}


@Preview
@Composable
fun MemberListItemViewPreview(){
    MemberListItemView(
        membership= PoliticMembership(
            id= "",
            role= "test",
            mayor= true, votingRight= true,
            startDate= null,
            endDate= null,
            person= PoliticPerson(
                id= "",
                lastName= "Max",
                firstName= "Musterman",
                title= arrayListOf(),
                formOfAddress= "Herr",
                gender= "String",
                phone= arrayListOf(),
                email= arrayListOf(),
                life= "String",
                lifeSource= "String",
                webUrl= "",
                updatedAt= LocalDateTime(0,1,1,0,0,0,0),
                createdAt= LocalDateTime(0,1,1,0,0,0,0),
            ), onBehalfOf= "",
            webUrl= "",
            updatedAt= LocalDateTime(0,1,1,0,0,0,0),
            createdAt= LocalDateTime(0,1,1,0,0,0,0),
        )
    )
}

