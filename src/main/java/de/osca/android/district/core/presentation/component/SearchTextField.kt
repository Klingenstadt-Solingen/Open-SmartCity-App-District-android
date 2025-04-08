package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.DISABLED_COLOR

@Composable
fun SearchTextField(
    text: String,
    onValueChange: (String) -> (Unit),
    onTrailingIcon: () -> (Unit),
) {
    val semanticsClearSearch = stringResource(R.string.clear_search)

    val interactionSource = remember { MutableInteractionSource() }

    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier =
            Modifier.fillMaxWidth()
                .height(38.dp),
        // accessibility requires a min height of 48.dp, which is the default
        singleLine = true,
        interactionSource = interactionSource,
        keyboardOptions =
            KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions =
            KeyboardActions(
                onDone = { focusManager.clearFocus() },
            ),
    ) { innerTextField ->
        @OptIn(ExperimentalMaterial3Api::class)
        OutlinedTextFieldDefaults.DecorationBox(
            value = text,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_magnifying_glass),
                    contentDescription = null,
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cross),
                    contentDescription = semanticsClearSearch,
                    modifier =
                        Modifier.clickable {
                            onTrailingIcon.invoke()
                        },
                )
            },
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedContainerColor = DISABLED_COLOR,
                    unfocusedContainerColor = DISABLED_COLOR,
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = DISABLED_COLOR,
                    unfocusedLeadingIconColor = Color.LightGray,
                    focusedLeadingIconColor = Color.LightGray,
                    // accessibility requires clickable fields to have a minimum contrast
                    // unfocusedTrailingIconColor = Color.LightGray,
                    // focusedTrailingIconColor = Color.LightGray,
                ),
            contentPadding =
                TextFieldDefaults.contentPaddingWithoutLabel(
                    top = 0.dp,
                    bottom = 0.dp,
                ),
        )
    }
}
