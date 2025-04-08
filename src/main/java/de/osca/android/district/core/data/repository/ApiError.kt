package de.osca.android.district.core.data.repository

import de.osca.android.district.R

sealed class ApiError : Exception() {
    data object Timeout : ApiError() {
        private fun readResolve(): Any = Timeout

        override fun messageResource(): Int = R.string.error_message_timeout
    }

    data object NoCacheResult : ApiError() {
        private fun readResolve(): Any = NoCacheResult

        override fun messageResource(): Int = R.string.error_message_unknown
    }

    data object NoResult : ApiError() {
        private fun readResolve(): Any = NoResult

        override fun messageResource(): Int = R.string.error_message_no_result
    }

    data object ConnectionError : ApiError() {
        private fun readResolve(): Any = ConnectionError

        override fun messageResource(): Int = R.string.error_message_connection_error
    }

    data class Unknown(val error: Exception) : ApiError() {
        override fun messageResource(): Int = R.string.error_message_unknown
    }

    abstract fun messageResource(): Int
}
