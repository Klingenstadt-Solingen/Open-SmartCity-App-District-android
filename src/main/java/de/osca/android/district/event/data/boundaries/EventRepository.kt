package de.osca.android.district.event.data.boundaries

import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.data.model.EventBooth
import de.osca.android.district.event.data.model.EventOpeningHour
import de.osca.android.district.event.data.model.EventSponsor
import de.osca.android.district.event.data.model.EventTag
import java.util.Date

interface EventRepository {
    @Throws(ApiError::class)
    suspend fun getEventById(objectId: String): Event

    @Throws(ApiError::class)
    suspend fun getEvents(
        districtState: DistrictState = DistrictState.ALL,
        dateRangeMin: Date?,
        dateRangeMax: Date?,
        bookmarkIds: List<String>?,
        limit: Int = 50,
        skip: Int = 0,
        searchText: String? = null,
    ): List<Event>

    suspend fun getNewEventCount(
        watchedAt: Date,
        districtState: DistrictState,
    ): Int


    @Throws(ApiError::class)
    suspend fun getNextEvents(
        districtState: DistrictState = DistrictState.ALL,
    ): List<Event>

    @Throws(ApiError::class)
    suspend fun getEventBoothsByEventId(objectId: String): List<EventBooth>

    @Throws(ApiError::class)
    suspend fun getEventTagsByEventBoothId(objectId: String): List<EventTag>

    @Throws(ApiError::class)
    suspend fun getEventSponsorByEventId(objectId: String): List<EventSponsor>

    @Throws(ApiError::class)
    suspend fun getEventBoothSponsorByEventBoothId(objectId: String): List<EventSponsor>

    @Throws(ApiError::class)
    suspend fun getEventOpeningHoursByEventId(objectId: String): List<EventOpeningHour>
}
