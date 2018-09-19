package io.data2viz.viz


/**
 * Common interface for all platform renderers.
 */
interface VizRenderer {

    fun render(viz: Viz)

    /**
     * Starts all animations
     */
    fun startAnimations()

    /**
     * Stops all animations.
     */
    fun stopAnimations()
}