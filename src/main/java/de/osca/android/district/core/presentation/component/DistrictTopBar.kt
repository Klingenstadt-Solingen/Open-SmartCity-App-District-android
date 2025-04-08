package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import de.osca.android.district.R
import de.osca.android.district.core.navigation.LocalNavigationController
import de.osca.android.district.core.presentation.design.DistrictDesign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistrictTopBar(
    actions: @Composable () -> (Unit) = {DistrictToolbar()},
    title: @Composable () -> (Unit) = {
        val navController = LocalNavigationController.current
        if (navController.previousBackStackEntry == null) {
            Text(
                stringResource(id = R.string.dashboard_city_name),
                style =
                TextStyle(
                    fontSize = DistrictDesign.Size.Font.HEADLINE,
                    fontWeight = FontWeight.Bold,
                ),
            )
        } else {
            BackButton(modifier = Modifier.fillMaxHeight())
        }
    },

) {

    TopAppBar(
        colors =
        TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        title = title,
        actions = {
            actions()
        },
    )
}
