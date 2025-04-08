package de.osca.android.district.core.navigation

import androidx.annotation.RestrictTo
import androidx.navigation.NavGraphBuilder

interface NavGraphDestination {
    fun NavGraphBuilder.destination()

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun callDestination(navigationBuilder: NavGraphBuilder) {
        navigationBuilder.destination()
    }
}
