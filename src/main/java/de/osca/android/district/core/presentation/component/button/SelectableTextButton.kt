package de.osca.android.district.core.presentation.component.button

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.presentation.design.primary

@Composable
fun SelectableTextButton(
    @StringRes id: Int,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    SelectableTextButton(
        text = stringResource(id = id),
        modifier = modifier,
        onClick = onClick,
        selected = selected,
        enabled = enabled,
    )
}

@Composable
fun SelectableTextButton(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    SelectableButton(
        modifier = modifier,
        onClick = onClick,
        selected = selected,
        enabled = enabled,
        content = {
            Text(
                text = text,
                maxLines = 1,
                style =
                    defaultTextStyle(
                        DistrictDesign.Size.Font.SUB_SUB_TITLE,
                        if (selected) Color.primary else Color.Black,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    ),
            )
        },
    )
}
