package de.osca.android.district.core.util

import de.osca.android.district.core.navigation.NestedCoreNavItems
import de.osca.android.district.dashboard.navigation.DashboardNavItems
import de.osca.android.district.event.navigation.EventNavItems
import de.osca.android.district.pointofinterest.navigation.PointOfInterestNavItems
import de.osca.android.district.politics.navigation.PoliticsNavItems
import de.osca.android.district.pressrelease.navigation.PressReleaseNavItems
import de.osca.android.district.project.navigation.ProjectNavItems
import de.osca.android.district.weather.navigation.WeatherNavItems

fun getDistrictRouteString(routeString: String): String? {
    when (routeString.split("/").first()) {
        NestedCoreNavItems.NestedCoreNavItem::class.qualifiedName -> return "district"
        DashboardNavItems.DashboardNavItem::class.qualifiedName -> return "district/dashboard"
        EventNavItems.EventDetailMapNavItem::class.qualifiedName -> return "district/events/map"
        EventNavItems.EventDetailNavItem::class.qualifiedName -> return "district/events/detail"
        EventNavItems.EventListNavItem::class.qualifiedName -> return "district/events"
        PointOfInterestNavItems.PointOfInterestNavItem::class.qualifiedName -> return "district/poi"
        PoliticsNavItems.PoliticsNavItem::class.qualifiedName -> return "district/politics"
        PoliticsNavItems.PoliticsDetailsNavItem::class.qualifiedName -> return "district/politics/detail"
        PoliticsNavItems.PoliticsMembersNavItem::class.qualifiedName -> return "district/politics/members"
        PoliticsNavItems.PoliticsMemberDetailNavItem::class.qualifiedName -> return "district/politics/members/detail"
        PoliticsNavItems.PoliticsMeetingsNavItem::class.qualifiedName -> return "district/politics/meetings"
        PoliticsNavItems.PoliticsMeetingDetailNavItem::class.qualifiedName -> return "district/politics/meetings/detail"
        PressReleaseNavItems.PressReleaseNavItem::class.qualifiedName -> return "district/pressreleases"
        PressReleaseNavItems.PressReleaseDetailNavItem::class.qualifiedName -> return "district/pressreleases/detail"
        ProjectNavItems.ProjectNavItem::class.qualifiedName -> return "district/project"
        ProjectNavItems.ProjectDetailsNavItem::class.qualifiedName -> return "district/project/detail"
        WeatherNavItems.WeatherScreenNavItem::class.qualifiedName -> return "district/weather"
    }

    return null
}
