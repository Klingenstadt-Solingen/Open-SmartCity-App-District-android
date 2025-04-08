package de.osca.android.district.politics.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.politics.data.model.PoliticMeeting
import de.osca.android.district.politics.data.model.PoliticMembership
import de.osca.android.district.politics.presentation.screen.PoliticDistrictDetailScreen
import de.osca.android.district.politics.presentation.screen.PoliticDistrictListScreen
import de.osca.android.district.politics.presentation.screen.PoliticMeetingDetailScreen
import de.osca.android.district.politics.presentation.screen.PoliticMeetingListScreen
import de.osca.android.district.politics.presentation.screen.PoliticMemberDetailScreen
import de.osca.android.district.politics.presentation.screen.PoliticMemberListScreen
import kotlinx.serialization.json.Json


class PoliticsDestination : NavGraphDestination {
    override fun NavGraphBuilder.destination() {
        /** Project Grid **/
        composable<PoliticsNavItems.PoliticsNavItem> {
            PoliticDistrictListScreen()
        }

        /** Project Details **/
        composable<PoliticsNavItems.PoliticsDetailsNavItem>{ //(typeMap = mapOf(typeOf<District>() to parcelableType<District>())) {
            val districtId = it.toRoute<PoliticsNavItems.PoliticsDetailsNavItem>().districtId
            val logoUrl = it.toRoute<PoliticsNavItems.PoliticsDetailsNavItem>().logoUrl
            val area = it.toRoute<PoliticsNavItems.PoliticsDetailsNavItem>().serialArea
            PoliticDistrictDetailScreen(
                districtId = districtId,
                logoUrl = logoUrl,
                area = Json.decodeFromString<List<Pair<Double,Double>>>(area)
            )
        }

        composable<PoliticsNavItems.PoliticsMembersNavItem> {
            val organizationId = it.toRoute<PoliticsNavItems.PoliticsMembersNavItem>().organizationId
            val organizationTitle = it.toRoute<PoliticsNavItems.PoliticsMeetingsNavItem>().organizationTitle
            PoliticMemberListScreen(organizationId = organizationId, organizationTitle = organizationTitle)
        }

        composable<PoliticsNavItems.PoliticsMemberDetailNavItem> {
            val member = it.toRoute<PoliticsNavItems.PoliticsMemberDetailNavItem>().membership
            PoliticMemberDetailScreen(member= Json.decodeFromString<PoliticMembership>(member))
        }

        composable<PoliticsNavItems.PoliticsMeetingsNavItem> {
            val organizationId = it.toRoute<PoliticsNavItems.PoliticsMeetingsNavItem>().organizationId
            val organizationTitle = it.toRoute<PoliticsNavItems.PoliticsMeetingsNavItem>().organizationTitle
            PoliticMeetingListScreen(organizationId = organizationId, organizationTitle= organizationTitle)
        }

        composable<PoliticsNavItems.PoliticsMeetingDetailNavItem> {
            val meeting = it.toRoute<PoliticsNavItems.PoliticsMeetingDetailNavItem>().meeting
            PoliticMeetingDetailScreen(meeting = Json.decodeFromString<PoliticMeeting>(meeting))
        }
    }
}
