package de.osca.android.district.core.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

/**
 * Debounces the execution of [debounce]
 *
 * @param scope that holds the executed action, if the [scope] is cancelled, the action terminates too.
 * Using a scope handled by a composable will cancel the action if the composable is disposed.
 * Use a view model scope or the [GlobalScope] to outlive composables.
 */
class Debouncer(
    private val delayDuration: Duration = 300.milliseconds,
    private val timeout: Duration = 1.minutes,
    private val debugTag: String? = null,
    private val scope: CoroutineScope,
) {
    private var lastJob: Job? = null

    fun debounce(
        delayDuration: Duration? = null,
        timeout: Duration? = null,
        action: CoroutineScope.() -> Unit,
    ) {
        val delayOverride = delayDuration ?: this.delayDuration
        val timeoutOverride = timeout ?: this.timeout

        lastJob?.cancel()
        lastJob =
            scope
                .launch {
                    try {
                        withTimeout(timeoutOverride) {
                            delay(delayOverride.inWholeMilliseconds)
                            action()
                        }
                    } catch (e: TimeoutCancellationException) {
                        Log.d(
                            "Debounce",
                            "debounce${if (debugTag != null) "($debugTag)" else ""}: cancelled after timeout ($timeoutOverride) exceeded",
                        )
                    }
                }
    }
}
