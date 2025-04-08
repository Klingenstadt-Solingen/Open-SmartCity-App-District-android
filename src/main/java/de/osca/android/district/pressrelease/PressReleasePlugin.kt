package de.osca.android.district.pressrelease

import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.pressrelease.navigation.PressReleaseDestination
import de.osca.android.district.pressrelease.navigation.PressReleaseTileWrapper
import de.osca.android.district.pressrelease.navigation.PressReleaseWidgetWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PressReleasePlugin
    @Inject
    constructor() : Plugin {
        override val navDestinations: NavGraphDestination = PressReleaseDestination()
        override val dashboardTiles =
            listOf(
                PressReleaseTileWrapper,
            )
        override val dashboardWidgets = listOf(PressReleaseWidgetWrapper)
    }
