package de.osca.android.district.event.data.repository

import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.coroutines.getById
import com.parse.coroutines.suspendCount
import com.parse.coroutines.suspendFind
import com.parse.ktx.include
import com.parse.ktx.orderByAscending
import com.parse.ktx.whereContains
import com.parse.ktx.whereDoesNotExist
import com.parse.ktx.whereGreaterThan
import com.parse.ktx.whereGreaterThanOrEqualTo
import com.parse.ktx.whereLessThan
import com.parse.ktx.whereLessThanOrEqualTo
import com.parse.ktx.whereMatches
import com.parse.ktx.whereMatchesQuery
import de.osca.android.district.core.CACHE_TIME
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.core.data.repository.catchParse
import de.osca.android.district.core.util.applyDistrictFilter
import de.osca.android.district.core.util.atSameDay
import de.osca.android.district.core.util.toLocalDateTime
import de.osca.android.district.event.data.boundaries.EventRepository
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.data.model.EventBooth
import de.osca.android.district.event.data.model.EventOpeningHour
import de.osca.android.district.event.data.model.EventSponsor
import de.osca.android.district.event.data.model.EventTag
import de.osca.android.district.event.data.model.EventType
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class EventRepositoryImpl
    @Inject
    constructor() : EventRepository {
        override suspend fun getNewEventCount(
            watchedAt: Date,
            districtState: DistrictState,
        ): Int {
            val queryEnd = ParseQuery.getQuery(Event::class.java)
            val queryNoEnd = ParseQuery.getQuery(Event::class.java)

            val earlyDate = Calendar.getInstance()
            earlyDate.time = Date()
            earlyDate.set(Calendar.HOUR_OF_DAY, 0)
            earlyDate.set(Calendar.MINUTE, 0)
            earlyDate.set(Calendar.SECOND, 0)
            val lateDate = Calendar.getInstance()
            lateDate.time = Date()
            lateDate.set(Calendar.HOUR_OF_DAY, 23)
            lateDate.set(Calendar.MINUTE, 59)
            lateDate.set(Calendar.SECOND, 59)

            queryEnd.whereLessThan(Event::startDate, lateDate.time)
            queryEnd.whereGreaterThan(
                Event::endDate,
                Date(),
            )

            queryNoEnd.whereGreaterThan(Event::startDate, earlyDate.time)
            queryNoEnd.whereLessThan(Event::startDate, lateDate.time)
            queryNoEnd.whereDoesNotExist(Event::endDate)

            val query = ParseQuery.or(listOf(queryEnd, queryNoEnd))

            watchedAt.let {
                query.whereGreaterThan("updatedAt", watchedAt)
            }

            query.applyDistrictFilter(districtState)
            query.whereContainedIn("eventStatus", listOf("scheduled", "postponed", "canceled"))
            query.include(Event::type)
            query.setCachePolicy(ParseQuery.CachePolicy.IGNORE_CACHE)
            return try {
                query.suspendCount()
            } catch (_: ParseException) {
                0
            }
        }

        @Throws(ApiError::class)
        override suspend fun getNextEvents(districtState: DistrictState): List<Event> {
            val query = ParseQuery.getQuery(Event::class.java)
            query.include("type")
            val date = Date()
            query.whereLessThan(Event::startDate, date)
            query.whereGreaterThan(Event::endDate, date)
            query.orderByAscending(Event::startDate)
            query.applyDistrictFilter(districtState)

            val typeQuery =
                ParseQuery.getQuery(EventType::class.java)
                    .whereMatches(EventType::name, "Großveranstaltung")
            query.whereMatchesQuery(Event::type, typeQuery)
            query.limit = 1
            query.whereContainedIn("eventStatus", listOf("scheduled", "postponed", "canceled"))
            query.setMaxCacheAge(CACHE_TIME)
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return catchParse { query.suspendFind() }
        }

        @Throws(ApiError::class)
        override suspend fun getEventById(objectId: String): Event {
            val query = ParseQuery.getQuery(Event::class.java)
            query.include("type")
            query.setMaxCacheAge(CACHE_TIME)
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return catchParse { query.getById(objectId) }
        }

        @Throws(ApiError::class)
        override suspend fun getEvents(
            districtState: DistrictState,
            dateRangeMin: Date?,
            dateRangeMax: Date?,
            bookmarkIds: List<String>?,
            limit: Int,
            skip: Int,
            searchText: String?,
        ): List<Event> {
            val query =
                if (dateRangeMin != null && dateRangeMax != null) {
                    val queryEnd = ParseQuery.getQuery(Event::class.java)
                    val queryNoEnd = ParseQuery.getQuery(Event::class.java)

                    queryNoEnd.whereGreaterThanOrEqualTo(Event::startDate, dateRangeMin)
                    queryNoEnd.whereLessThanOrEqualTo(Event::startDate, dateRangeMax)
                    queryNoEnd.whereDoesNotExist(Event::endDate)

                    queryEnd.whereLessThanOrEqualTo(Event::startDate, dateRangeMax)

                    val now = Date()

                    if (now.toLocalDateTime().atSameDay(dateRangeMin.toLocalDateTime())) {
                        queryEnd.whereGreaterThanOrEqualTo(Event::endDate, Date())
                    } else {
                        queryEnd.whereGreaterThanOrEqualTo(Event::endDate, dateRangeMin)
                    }

                    val queryList = listOf<ParseQuery<Event>>(queryNoEnd, queryEnd)
                    ParseQuery.or(queryList)
                } else {
                    ParseQuery.getQuery(Event::class.java)
                }

            bookmarkIds?.let {
                query.whereContainedIn("objectId", it)
            }

            if (!searchText.isNullOrEmpty()) {
                query.whereContains(Event::name, searchText)
            }

            query.applyDistrictFilter(districtState)
            query.whereContainedIn("eventStatus", listOf("scheduled", "postponed", "canceled"))
            query.include(Event::type)
            query.setMaxCacheAge(CACHE_TIME).setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            query.orderByAscending(Event::startDate)
            query.limit = limit
            query.skip = skip

            return catchParse(query::suspendFind)
        }

        @Throws(ApiError::class)
        override suspend fun getEventBoothsByEventId(objectId: String): List<EventBooth> {
            val query = ParseQuery.getQuery(EventBooth::class.java)
            query.whereEqualTo("event", objectId)
            query.include(EventBooth::type)
            query.include(EventBooth::mainSponsor)
            query.setMaxCacheAge(CACHE_TIME)
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return catchParse(query::suspendFind)
        }

        @Throws(ApiError::class)
        override suspend fun getEventTagsByEventBoothId(objectId: String): List<EventTag> {
            val query =
                ParseObject.createWithoutData(EventBooth::class.java, objectId)
                    .tags.query
            query.setMaxCacheAge(CACHE_TIME)
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return catchParse(query::suspendFind)
        }

        @Throws(ApiError::class)
        override suspend fun getEventSponsorByEventId(objectId: String): List<EventSponsor> {
            val query =
                ParseObject.createWithoutData(Event::class.java, objectId)
                    .sponsors.query
            query.setMaxCacheAge(CACHE_TIME)
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return catchParse(query::suspendFind)
        }

        @Throws(ApiError::class)
        override suspend fun getEventBoothSponsorByEventBoothId(objectId: String): List<EventSponsor> {
            val query =
                ParseObject.createWithoutData(EventBooth::class.java, objectId)
                    .sponsors.query
            query.setMaxCacheAge(CACHE_TIME)
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return catchParse(query::suspendFind)
        }

        @Throws(ApiError::class)
        override suspend fun getEventOpeningHoursByEventId(objectId: String): List<EventOpeningHour> {
            val query = ParseQuery.getQuery(EventOpeningHour::class.java)
            query.whereEqualTo("event", objectId)
            query.setMaxCacheAge(CACHE_TIME)
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return catchParse(query::suspendFind)
        }
    }
