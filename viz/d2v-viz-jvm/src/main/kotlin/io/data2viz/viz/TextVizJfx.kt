package io.data2viz.viz

import javafx.beans.value.ObservableValue
import javafx.geometry.Bounds
import javafx.geometry.VPos
import javafx.scene.Group
import javafx.scene.text.Text


/**
 * TextViz Jfx implementation. As Text Jfx has no anchor property we must implement
 * it.
 */
class TextVizJfx(val parent: Group, val text: Text) : TextVizItem,
        StyledElement by StyleDelegate(text),
        HasFill by FillDelegate(text),
        Transformable by TransformNodeDelegate(text) {


    override var textContent: String
        get() = text.text
        set(value) {
            text.text = value
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
        text.x = _x - deltaX
    }

    private var _anchor = TextAnchor.START
    private var deltaX = 0.0

    override var anchor: TextAnchor
        get() = _anchor
        set(value) {
            _anchor = value
            text.layoutBoundsProperty().removeListener(::updateDeltaX)
            text.layoutBoundsProperty().addListener(::updateDeltaX)
            updateDeltaX(text.layoutBoundsProperty(), text.layoutBounds, text.layoutBounds) // first two params are not used.
        }

    private fun updateDeltaX(value: ObservableValue<out Bounds>, old: Bounds, newBounds: Bounds) {
        deltaX = when (_anchor) {
            TextAnchor.START -> 0.0
            TextAnchor.MIDDLE -> newBounds.width / 2
            TextAnchor.END -> newBounds.width
        }
        updateXWithAnchor()
    }


    override var y: Double by DoublePropertyDelegate(text.yProperty())

    private var _baseline = TextAlignmentBaseline.BASELINE
    override var baseline: TextAlignmentBaseline
        get() = _baseline
        set(value) {
            _baseline = value
            text.textOrigin = when (value){
                TextAlignmentBaseline.BASELINE -> VPos.BASELINE
                TextAlignmentBaseline.HANGING -> VPos.TOP
                TextAlignmentBaseline.MIDDLE -> VPos.CENTER
            }
        }
}