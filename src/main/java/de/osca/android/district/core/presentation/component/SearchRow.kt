package de.osca.android.district.core.presentation.component

import androidx.compose.runtime.Composable

@Composable
fun SearchRow(
    searchText: String,
    setSearchText: (String) -> (Unit),
) {
    // TODO: Style
    SearchTextField(
        text = searchText,
        onValueChange = {
            setSearchText(it)
        },
        onTrailingIcon = {
            setSearchText("")
        },
    )
}
