package de.osca.android.district.core.presentation.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import de.osca.android.district.core.navigation.LocalNavigationController

@Composable
fun <T : Any> NavigationLink(
    navigate: T?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues? = PaddingValues(0.dp),
    shape: Shape? = null,
    elevation: CardElevation? = null,
    colors: CardColors? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val navController = LocalNavigationController.current

    GeneralButton(
        onClick = {
            if (navigate != null) {
                navController.navigate(navigate)
            }
        },
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}
