package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.button.SelectableButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.primary

@Composable
fun EventDayCalendarButton(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    SelectableButton(
        onClick = onClick,
        modifier = modifier,
        selected = selected,
        enabled = enabled,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Calendar",
                modifier =
                    Modifier
                        .size(DistrictDesign.Size.Icon.BIG),
                tint = Color.primary,
            )
        },
    )
}
