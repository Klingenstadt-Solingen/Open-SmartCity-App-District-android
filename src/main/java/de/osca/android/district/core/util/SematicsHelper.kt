package de.osca.android.district.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.osca.android.district.R

@Composable
fun contentDescriptionFromCountAndName(
    count: Int?,
    name: Int,
): String {
    var description: String? = null
    count?.let {
        if (it > 0) {
            description =
                stringResource(
                    id = R.string.new_items,
                    if (it > 99) {
                        stringResource(id = R.string.many)
                    } else {
                        it.toString()
                    },
                    stringResource(id = name),
                )
        }
    }

    return description ?: stringResource(id = name)
}
