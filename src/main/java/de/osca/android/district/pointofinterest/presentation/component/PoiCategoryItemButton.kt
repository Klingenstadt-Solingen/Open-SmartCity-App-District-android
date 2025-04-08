package de.osca.android.district.pointofinterest.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.accentColor
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.pointofinterest.data.model.PoiCategory

const val UNCHECKED_OPACITY = 0.5f

// TODO(Alex): Refactor no multipurpose views

/**
 * @param signalizeSelected if true, the Button is communicated as a selectable Checkbox via semantics.
 * Otherwise, the user perceives this as a Button and is not informed about the [checked] state
 *
 * Corresponds to PoiCategoryItemView in Xcode
 */
@Composable
fun PoiCategoryItemButton(
    category: PoiCategory,
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    width: Dp = 80.dp,
    signalizeSelected: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit)?,
) {
    Box(
        modifier =
            modifier
                .width(width)
                .clickable {
                    onCheckedChange?.invoke(!checked)
                }.alpha(if (checked) 1f else UNCHECKED_OPACITY)
                .semantics {
                    if (signalizeSelected) {
                        role = Role.Checkbox
                        selected = checked
                    } else {
                        role = Role.Button
                    }
                },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(DistrictDesign.Padding.MEDIUM),
        ) {
            Surface(
                color = Color.accentColor,
                shape = CircleShape,
                shadowElevation = DistrictDesign.ELEVATION_MEDIUM,
            ) {
                Icon(
                    painter = painterResource(id = getIcon(category)),
                    tint = Color.primary,
                    contentDescription = "",
                    modifier =
                        Modifier
                            .size(DistrictDesign.Size.Icon.HUGE)
                            .padding(DistrictDesign.Padding.MEDIUM),
                )
            }
            Text(
                category.name,
                style = defaultTextStyle(DistrictDesign.Size.Font.NORMAL_TEXT),
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

// TODO(Niklas): Remove hardcoded values (copied from iOS)
fun getIcon(category: PoiCategory): Int =
    when (category.sourceId) {
        "entsorgung15" -> R.drawable.ic_wifi
        "dienstleistung100" -> R.drawable.ic_shopping_bag
        "sport26" -> R.drawable.ic_outdoor_swing
        "sport38" -> R.drawable.ic_running_person
        "verkehr41" -> R.drawable.ic_car_with_plug
        "tourismus1" -> R.drawable.ic_cloud_with_kite
        "parken1" -> R.drawable.ic_car_with_p
        "uebernachtung3" -> R.drawable.ic_bed_with_person
        "verkehr18" -> R.drawable.ic_construction_barrier
        "verwaltung90" -> R.drawable.ic_chat_bubles
        else -> R.drawable.ic_clock
    }
