package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.button.GeneralButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.accentColor
import de.osca.android.district.core.presentation.design.primary

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    iconPadding: Dp = DistrictDesign.Padding.MEDIUM,
    onClick: () -> Unit,
) {
    GeneralButton(
        contentPadding = PaddingValues(iconPadding),
        colors = CardDefaults.cardColors(containerColor = Color.accentColor),
        shape = DistrictDesign.ROUNDED_SHAPE,
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            painter =
                if (isFavorite) {
                    painterResource(id = R.drawable.ic_fav_enabled)
                } else {
                    painterResource(
                        id = R.drawable.ic_fav_disabled,
                    )
                },
            contentDescription = null,
            modifier = Modifier.size(DistrictDesign.Size.Icon.BIG),
            tint = Color.primary
        )
    }
}
