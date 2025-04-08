package de.osca.android.district.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.district.R
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.navigation.NestedCoreNavItems
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.dashboard.presentation.component.DashboardImage
import de.osca.android.essentials.presentation.component.design.BaseListContainer
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs
import de.osca.android.essentials.presentation.component.design.ModuleDesignArgs

@Composable
fun DistrictWidget(
    navController: NavController,
    @StringRes cityName: Int = -1,
    districtWidgetViewModel: DistrictWidgetViewModel = hiltViewModel(),
    districtViewModel: DistrictViewModel,
    masterDesignArgs: MasterDesignArgs = districtWidgetViewModel.defaultDesignArgs,
) {
    val districtState = districtViewModel.districtState
    val title =
        if (districtState is DistrictState.DISTRICT) {
            stringResource(
                id = R.string.district_prefix,
                districtState.district.name,
            )
        } else {
            stringResource(id = R.string.dashboard_city_name)
        }

    BaseListContainer(
        text = stringResource(R.string.district_module, stringResource(cityName)),
        showMoreOption = masterDesignArgs.vWidgetShowMoreOption,
        moduleDesignArgs = DistrictModuleDesignArgs(),
        onMoreOptionText = when (districtState is DistrictState.DISTRICT) {
            true -> stringResource(
                id = R.string.show_district,
                districtState.district.name,
            )
            false -> stringResource(
                id = R.string.show_all
            )
        },
        onMoreOptionClick = {
            navController.navigate(NestedCoreNavItems.NestedCoreNavItem)
        },
        masterDesignArgs = masterDesignArgs,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(150.dp).clip(DistrictDesign.ROUNDED_SHAPE)
                .clickable {
                    navController.navigate(NestedCoreNavItems.NestedCoreNavItem)
            },
            contentAlignment = Alignment.Center,
        ) {
            DashboardImage()
            Text(
                title,
                style =
                    TextStyle(
                        fontSize = DistrictDesign.Size.Font.HEADLINE,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
            )
        }
    }
}

class DistrictModuleDesignArgs : ModuleDesignArgs {
    override val mTopBarBackColor: Color? = null
    override val mBottomBarBackColor: Color? = null
    override val mStatusBarBackColor: Color? = null
    override val mCardBackColor: Color? = null
    override val mMenuBackColor: Color? = null
    override val mSheetBackColor: Color? = null
    override val mScreenBackColor: Color? = null
    override val mTopBarTextColor: Color? = null
    override val mBottomBarTextColor: Color? = null
    override val mStatusBarTextColor: Color? = null
    override val mCardTextColor: Color? = null
    override val mMenuTextColor: Color? = null
    override val mSheetTextColor: Color? = null
    override val mScreenTextColor: Color? = null
    override val mHintTextColor: Color? = null
    override val mTextInCard: Boolean? = null
    override val mRootCardSpacing: Dp? = null
    override val mRootBoarderSpacing: Dp? = null
    override val mCardContentPadding: Dp? = null
    override val mConstrainHeight: Dp? = null
    override val mCardElevation: Dp? = null
    override val mSheetElevation: Dp? = null
    override val mShowLessText: Int? = null
    override val mShowMoreText: Int? = null
    override val mHeaderTextColor: Color? = null
    override val mShowMoreTextColor: Color? = null
    override val vShowMoreOption: Boolean = true
    override val vModuleTitle: Int = R.string.district_module
    override val mShapeCard: Dp? = null
    override val mShapeBottomSheet: Shape? = null
    override val mShapeTopSheet: Shape? = null
    override val mShapeSheet: Shape? = null
    override val mButtonBackgroundColor: Color? = null
    override val mButtonContentColor: Color? = null
    override val mSwitchCheckedThumbColor: Color? = null
    override val mSwitchCheckedTrackColor: Color? = null
    override val mSwitchUncheckedThumbColor: Color? = null
    override val mSwitchUncheckedTrackColor: Color? = null
    override val mDropDownBorderColor: Color? = null
    override val mTextFieldFocusedBorderColor: Color? = null
    override val mTextFieldUnfocusedBorderColor: Color? = null
    override val mDialogsBackgroundColor: Color? = null
    override val mDialogsTextColor: Color? = null
    override val mDialogsBackColor: Color? = null
    override val mBorderSpace: Dp? = null
    override val mContentPaddingForMiniCards: Dp? = null
    override val mRoundIconSize: Dp? = null
    override val mIsStatusBarWhite: Boolean? = null
}
