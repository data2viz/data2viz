package io.data2viz.logging

/**
 * A platform independent marker to enrich log statements.
 */
actual interface Marker {

    actual fun getName(): String
}