package de.osca.android.district.core.presentation.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.accentColor

private val unselectedColor = CardColors(Color.White, Color.Black, Color.White, Color.Black)

@Composable
fun SelectableButton(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(DistrictDesign.Padding.MEDIUM),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable (() -> Unit),
) {
    val selectedColor = CardColors(Color.accentColor, Color.Black, Color.White, Color.Black)
    val elevation =
        CardDefaults.cardElevation(
            defaultElevation = DistrictDesign.ELEVATION_SMALL,
            pressedElevation = DistrictDesign.ELEVATION_SMALL,
        )

    val color =
        if (selected) {
            selectedColor
        } else {
            unselectedColor
        }

    Card(
        modifier =
            modifier.semantics {
                this.role = Role.RadioButton
                this.selected = selected
            },
        elevation = elevation,
        onClick = onClick,
        enabled = enabled,
        colors = color,
        shape = DistrictDesign.ROUNDED_SHAPE,
        border = border,
        interactionSource = interactionSource,
        content = {
            Box(Modifier.padding(contentPadding)) {
                content.invoke()
            }
        },
    )
}
