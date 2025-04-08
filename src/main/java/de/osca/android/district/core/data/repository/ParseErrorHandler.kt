package de.osca.android.district.core.data.repository

import com.parse.ParseException
import kotlinx.coroutines.coroutineScope

suspend fun <T> catchParse(block: suspend () -> T): T =
    coroutineScope {
        try {
            block().run { this }
        } catch (e: ParseException) {
            throw when (e.code) {
                ParseException.TIMEOUT -> ApiError.Timeout
                ParseException.CONNECTION_FAILED -> ApiError.ConnectionError
                ParseException.CACHE_MISS -> ApiError.NoCacheResult
                ParseException.OBJECT_NOT_FOUND -> ApiError.NoResult
                else -> ApiError.Unknown(e)
            }
        }
    }
