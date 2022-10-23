package io.data2viz.viz

import kotlinx.browser.window
import org.w3c.dom.MediaQueryList
import org.w3c.dom.events.EventListener

internal object PixelRatioUpdater {

    /**
     * Automatically updated while [PixelRatioUpdater.dependentListenersCount] is positive.
     */
    var pixelRatio = window.devicePixelRatio
        get() = when (dependentListenersCount) {
            0 -> window.devicePixelRatio
            else -> field
        }

    /**
     * **WARNING:** Doesn't keep a strong reference to the passed [listener].
     * Make sure you keep one in the right object.
     */
    fun onChange(listener: () -> Unit) {
        try {
            listeners += WeakRef(listener)
            dependentListenersCount++
        } catch (e: Throwable) {
            console.error(e)
            console.error("This browser is outdated and doesn't support WeakRef. " +
                "As a result, changing the page zoom, or moving the window to " +
                "a screen with a different pixel density isn't properly supported. " +
                "Update or change your browser to avoid this issue.")
        }
    }
    private val listeners = mutableListOf<WeakRef<() -> Unit>>()

    private var dependentListenersCount = 0
        set(value) {
            when (value) {
                0 -> {
                    lastMediaQuery?.removeEventListener(type = "change", listener)
                    lastMediaQuery = null
                }

                1 -> updateValueAndListener()
                else -> check(value >= 0) {
                    "dependentListenersCount must always be positive. Tried to set it to $value"
                }
            }
            field = value
        }

    private val listener = EventListener { updateValueAndListener() }

    private fun updateValueAndListener() {
        val devicePixelRatio = window.devicePixelRatio
        pixelRatio = devicePixelRatio
        val iterator = listeners.listIterator()
        iterator.forEach { weakRef ->
            when (val listener = weakRef.deref()) {
                null -> iterator.remove().also {
                    dependentListenersCount--
                }
                else -> listener()
            }
        }

        // The media query works only when crossing the threshold, so we need to replace it
        // with an updated one after every change.
        val newMediaQuery = window.matchMedia("(resolution: ${devicePixelRatio}dppx)")
        newMediaQuery.addEventListener(type = "change", callback = listener)
        lastMediaQuery?.removeEventListener(type = "change", listener)
        lastMediaQuery = newMediaQuery
    }

    private var lastMediaQuery: MediaQueryList? = null
}
