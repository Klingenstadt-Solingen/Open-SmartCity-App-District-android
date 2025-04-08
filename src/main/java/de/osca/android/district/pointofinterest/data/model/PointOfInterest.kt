package de.osca.android.district.pointofinterest.data.model

import de.osca.android.essentials.domain.entity.ParsePOI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun getConvertedDetails(poi: ParsePOI): List<PointOfInterestDetail> =
    try {
        val jsonDecoder =
            Json {
                ignoreUnknownKeys = true
                isLenient = true
                explicitNulls = false
                encodeDefaults = true
                coerceInputValues = true
                prettyPrint = true
            }

        val j =
            poi.details.map { d ->

                val encode =
                    jsonDecoder.encodeToString(
                        d
                            .map { e -> e.key to e.value.toString() }
                            .toMap(),
                    )

                jsonDecoder.decodeFromString(
                    PointOfInterestDetail.serializer(),
                    encode,
                )
            }

        j.sortedBy { it.position }
    } catch (e: Exception) {
        emptyList()
    }
