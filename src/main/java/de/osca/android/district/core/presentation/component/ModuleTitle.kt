package de.osca.android.district.core.presentation.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle

@Composable
fun ModuleTitle(
    @StringRes title: Int,
    modifier: Modifier = Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
) {
    Text(
        text = stringResource(id = title),
        style =
            defaultTextStyle(
                fontSize = DistrictDesign.Size.Font.SUB_TITLE,
                fontWeight = FontWeight.Bold,
            ),
        modifier = modifier,
    )
}
