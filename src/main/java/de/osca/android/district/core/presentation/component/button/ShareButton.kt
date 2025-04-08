package de.osca.android.district.core.presentation.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import de.osca.android.district.R
import de.osca.android.district.core.PRIMARY_COLOR
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.util.startShareIntent

@Composable
fun ShareButton(
    url: String?,
    modifier: Modifier = Modifier.height(DistrictDesign.Size.Icon.BIG),
) {
    val context = LocalContext.current
    val semanticShare = stringResource(id = R.string.share)

    Icon(
        painter = painterResource(id = R.drawable.ic_share),
        contentDescription = "",
        tint = PRIMARY_COLOR,
        modifier =
            modifier.clearAndSetSemantics {
                contentDescription = "$semanticShare URL"
                role = Role.Button
            }.clickable {
                startShareIntent(url, context)
            },
    )
}
