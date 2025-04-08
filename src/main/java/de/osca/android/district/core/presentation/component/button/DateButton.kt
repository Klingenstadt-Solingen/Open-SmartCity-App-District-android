package de.osca.android.district.core.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.osca.android.district.core.presentation.component.datepicker.SimpleDatePickerDialog
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.disabledColor
import de.osca.android.district.core.util.formatDayMonthYear
import java.util.Date

@Composable
fun DateButton(
    date: Date,
    onDateChanged: (date: Date?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDatePicker: Boolean by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier =
            modifier.clickable { showDatePicker = true }
                .background(Color.disabledColor, DistrictDesign.ROUNDED_SHAPE_SMALL),
    ) {
        Text(
            text = date.formatDayMonthYear(),
            fontSize = DistrictDesign.Size.Font.DEFAULT,
            modifier =
                modifier.padding(
                    horizontal = DistrictDesign.Padding.MEDIUM,
                    vertical = DistrictDesign.Padding.SMALL,
                ),
        )
    }

    if (showDatePicker) {
        SimpleDatePickerDialog(
            initialDate = date.time,
            onConfirm = { millis ->
                onDateChanged.invoke(if (millis != null) Date(millis) else null)
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            },
        )
    }
}
