package de.osca.android.district.core.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.screen.DistrictPreferenceScreen

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun DistrictToolbar() {
    @OptIn(ExperimentalMaterial3Api::class)
    val sheetState =
        rememberModalBottomSheetState()
    var showPreferences by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            showPreferences = true
        },
    ) {
        Image(
            // TODO: Rename ic_district_person -> ic_person if project is standalone
            painter = painterResource(id = R.drawable.ic_district_person),
            contentDescription = stringResource(id = R.string.settings_button),
            modifier = Modifier.size(40.dp), // unique size
        )
    }
    val semanticsDistrict = stringResource(id = R.string.district_filter)

    if (showPreferences) {
        @OptIn(ExperimentalMaterial3Api::class)
        (
            ModalBottomSheet(
                onDismissRequest = {
                    showPreferences = false
                },
                sheetState = sheetState,
                containerColor = Color.White,
            ) {
                Box(
                    modifier =
                        Modifier.height(200.dp).semantics(true) {
                            contentDescription = semanticsDistrict
                        },
                ) {
                    DistrictPreferenceScreen()
                }
            }
        )
    }
}
