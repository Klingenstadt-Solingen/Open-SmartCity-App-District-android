package de.osca.android.district.core.presentation.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import de.osca.android.district.core.presentation.design.DistrictDesign

private val defaultColor = CardColors(Color.White, Color.Black, Color.White, Color.Black)

@Composable
fun GeneralButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentPadding: PaddingValues? = null,
    elevation: CardElevation? = null,
    colors: CardColors? = null,
    shape: Shape? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val mod =
        modifier
            .then(
                Modifier
                    .shadow(shape = DistrictDesign.ROUNDED_SHAPE, elevation = 0.dp),
            )
            .semantics {
                role = Role.Button
            }

    Card(
        elevation =
            elevation
                ?: CardDefaults.elevatedCardElevation(defaultElevation = DistrictDesign.ELEVATION_SMALL),
        modifier = mod,
        onClick = onClick,
        enabled = enabled,
        colors = colors ?: defaultColor,
        shape = shape ?: DistrictDesign.ROUNDED_SHAPE,
        border = border,
        interactionSource = interactionSource,
    ) {
        Box(Modifier.padding(contentPadding ?: PaddingValues(0.dp))) {
            content()
        }
    }
}
