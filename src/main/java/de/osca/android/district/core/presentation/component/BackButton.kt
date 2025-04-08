package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.navigation.LocalNavigationController
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val controller = LocalNavigationController.current

    TextButton(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 0.dp),
        onClick = { controller.popBackStack() },
        enabled = enabled,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_left),
            contentDescription = "",
            modifier = Modifier.padding(end = DistrictDesign.Padding.SMALL),
        )
        Text(
            text = stringResource(id = R.string.back_button),
            style =
                defaultTextStyle(),
        )
    }
}
