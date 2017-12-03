package io.data2viz.viz

import javafx.beans.value.ObservableValue
import javafx.geometry.Bounds
import javafx.scene.Group
import javafx.scene.text.Text


/**
 * TextViz Jfx implementation. As Text Jfx has no anchor property we must implement 
 * it.
 */
class TextVizJfx(val parent: Group, val text: Text) : TextVizItem,
        HasFill by FillDelegate(text),
        Transformable by TransformNodeDelegate(text) {

    override var y: Double by DoublePropertyDelegate(text.yProperty())

    override var textContent: String
        get() = text.text
        set(value) { text.text = value}

    var _x:Double = 0.0
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
            text.layoutBoundsProperty().removeListener(::updateDelta)
            text.layoutBoundsProperty().addListener(::updateDelta)
            updateDelta(text.layoutBoundsProperty(), text.layoutBounds, text.layoutBounds) // first two params are not used.
        }

    private fun updateDelta(value: ObservableValue<out Bounds>, old: Bounds, newBounds: Bounds){
        when(_anchor) {
            TextAnchor.START -> deltaX = 0.0
            TextAnchor.MIDDLE -> deltaX = newBounds.width / 2
            TextAnchor.END -> deltaX = newBounds.width
        }
        updateXWithAnchor()
    }

}