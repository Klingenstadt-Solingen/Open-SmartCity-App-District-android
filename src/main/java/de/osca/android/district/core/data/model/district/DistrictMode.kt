package de.osca.android.district.core.data.model.district

import androidx.annotation.StringRes
import de.osca.android.district.R

enum class DistrictMode(
    @StringRes val title: Int,
) {
    ALL(R.string.scope_all),
    DISTRICT(R.string.scope_district),
    NEARBY(R.string.scope_nearby),
}
