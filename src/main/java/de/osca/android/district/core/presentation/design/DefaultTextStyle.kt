package de.osca.android.district.core.presentation.design

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

fun defaultTextStyle(
    fontColor: Color = Color.Black,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
): TextStyle {
    return defaultTextStyle(DistrictDesign.Size.Font.DEFAULT, fontColor, fontStyle, fontWeight)
}

fun defaultTextStyle(
    fontSize: Int = DistrictDesign.Size.Font.DEFAULT.value.toInt(),
    fontColor: Color = Color.Black,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
): TextStyle {
    return TextStyle(
        fontSize = fontSize.sp,
        color = fontColor,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
    )
}

fun defaultTextStyle(
    fontSize: TextUnit = DistrictDesign.Size.Font.DEFAULT,
    fontColor: Color = Color.Black,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
): TextStyle {
    return TextStyle(
        fontSize = fontSize,
        color = fontColor,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
    )
}
