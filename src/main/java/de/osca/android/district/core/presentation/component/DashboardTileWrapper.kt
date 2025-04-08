package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import de.osca.android.district.core.navigation.NavItem
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.core.presentation.design.DistrictDesign

interface DashboardTileWrapper {
    val position: Int
    var routeItem: MutableState<NavItem>
    val tileContentDescription: MutableState<String>

    @Composable
    fun ContentView()

    @Composable
    fun Button(modifier: Modifier) {
        NavigationLink(
            navigate = routeItem.value,
            contentPadding = PaddingValues(),
            colors =
                CardColors(
                    contentColor = Color.Black,
                    disabledContentColor = Color.Gray,
                    containerColor = Color.White,
                    disabledContainerColor = Color.Gray,
                ),
            elevation =
                CardDefaults.elevatedCardElevation(
                    defaultElevation = DistrictDesign.ELEVATION_SMALL,
                    pressedElevation = DistrictDesign.ELEVATION_SMALL,
                ),
            shape = DistrictDesign.ROUNDED_SHAPE,
            modifier =
                modifier
                    .clearAndSetSemantics {
                        role = Role.Button
                        contentDescription = tileContentDescription.value
                    },
        ) {
            ContentView()
        }
    }
}
