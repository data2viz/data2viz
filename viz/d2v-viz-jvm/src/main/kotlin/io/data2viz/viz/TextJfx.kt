package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import javafx.beans.value.ObservableValue
import javafx.geometry.Bounds
import javafx.geometry.VPos


/**
 * TextViz Jfx implementation. As Text Jfx has no anchor property we must implement
 * it.
 */
class TextJfx(override val jfxElement: JfxText = JfxText()) : Text, JfxVizElement,
    StyledElement by StyleDelegate(jfxElement),
    HasFill,
    Transformable by TransformNodeDelegate(jfxElement) {

    override var fill: ColorOrGradient? by FillDelegate(this)


    override var textContent: String
        get() = jfxElement.text
        set(value) {
            jfxElement.text = value
        }

    var _x: Double = 0.0

    /**
     * Using deltaX in case of Anchor != TextAnchor.START
     */
    override var x: Double
        set(value) {
            _x = value
            updateXWithAnchor()
        }
        get() = _x

    private fun updateXWithAnchor() {
        jfxElement.x = _x - deltaX
    }

    private var _anchor = TextAnchor.START
    private var deltaX = 0.0

    override var anchor: TextAnchor
        get() = _anchor
        set(value) {
            _anchor = value
            jfxElement.layoutBoundsProperty().removeListener(::updateDeltaX)
            jfxElement.layoutBoundsProperty().addListener(::updateDeltaX)
            updateDeltaX(
                jfxElement.layoutBoundsProperty(),
                jfxElement.layoutBounds,
                jfxElement.layoutBounds
            ) // first two params are not used.
        }

    private fun updateDeltaX(value: ObservableValue<out Bounds>, old: Bounds, newBounds: Bounds) {
        deltaX = when (_anchor) {
            TextAnchor.START -> 0.0
            TextAnchor.MIDDLE -> newBounds.width / 2
            TextAnchor.END -> newBounds.width
        }
        updateXWithAnchor()
    }


    override var y: Double by DoublePropertyDelegate(jfxElement.yProperty())

    private var _baseline = TextAlignmentBaseline.BASELINE
    override var baseline: TextAlignmentBaseline
        get() = _baseline
        set(value) {
            _baseline = value
            jfxElement.textOrigin = when (value) {
                TextAlignmentBaseline.BASELINE -> VPos.BASELINE
                TextAlignmentBaseline.HANGING -> VPos.TOP
                TextAlignmentBaseline.MIDDLE -> VPos.CENTER
            }
        }
}