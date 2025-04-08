package de.osca.android.district.core.presentation.design

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import de.osca.android.district.R

val Color.Companion.primary: Color
    @Composable
    get() = colorResource(id = R.color.primaryColor)

val Color.Companion.accentColor: Color
    @Composable
    get() = colorResource(id = R.color.accentColor)

val Color.Companion.disabledColor: Color
    @Composable
    get() = colorResource(id = R.color.disabledColor)

val Color.Companion.linkColor: Color
    @Composable
    get() = Color(51, 102, 204)
