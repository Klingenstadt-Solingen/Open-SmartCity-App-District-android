package de.osca.android.district.core.presentation.component.pager

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.accentColor
import de.osca.android.district.core.presentation.design.disabledColor

@Composable
fun PagerButton(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    action: () -> Unit,
) {
    val animateStateButtonColor =
        animateColorAsState(
            targetValue = if (selected) Color.accentColor else Color.disabledColor,
            animationSpec = tween(200, 0, LinearEasing),
            label = "Pager Button Animation",
        )
    Button(
        onClick = action,
        enabled = !selected,
        shape = RectangleShape,
        colors =
            ButtonDefaults.buttonColors(
                disabledContainerColor = animateStateButtonColor.value,
                containerColor = animateStateButtonColor.value,
            ),
        modifier =
            modifier
                .height(if (selected) DistrictDesign.Size.Icon.SMALL + 1.dp else DistrictDesign.Size.Icon.SMALL),
    ) {}
}
