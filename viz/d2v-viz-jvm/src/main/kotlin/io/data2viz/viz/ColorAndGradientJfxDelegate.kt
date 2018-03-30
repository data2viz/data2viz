package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.interpolate.interpolateRgb
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.Stop
import kotlin.reflect.KProperty

typealias JfxColor = javafx.scene.paint.Color



class FillDelegate(val element: JfxVizElement) : StateProperty {
    val shape: JfxShape = element.jfxElement as JfxShape

    private var start: Color? = null
    private var end: Color? = null
    var iterator: ((Number) -> Color)? = null

    override fun setPercent(percent: Double) {
        val value = iterator?.let { it(percent) }
        shape.fill = value?.jfxColor
    }

    operator fun getValue(rectJfx: JfxVizElement, property: KProperty<*>): ColorOrGradient? {
        return (shape.fill as javafx.scene.paint.Color?)?.d2vColor
    }

    operator fun setValue(vizElement: JfxVizElement, property: KProperty<*>, colorOrGradient: ColorOrGradient?) {
        (element as? StateableElement)?.stateManager?.let {
            if (it.status == StateManagerStatus.RECORD) {
                //Todo manage null color with alpha
                val color = shape.fill as? JfxColor
                start = color?.d2vColor
                end = colorOrGradient as? Color
                it.addStateProperty(this)
                iterator = interpolateRgb(start!!, end!!)
                return
            }
        }

        shape.fill = when (colorOrGradient) {
            null -> null
            is Color -> colorOrGradient.jfxColor
            is LinearGradient -> colorOrGradient.toLinearGradientJFX()
            is RadialGradient -> colorOrGradient.toRadialGradientJFX()
            else -> throw IllegalStateException("$colorOrGradient not managed")
        }
    }
}


class StrokeDelegate(val shape: JfxShape) : HasStroke, StateProperty {

    private var start: Color? = null
    private var end: Color? = null
    var iterator: ((Number) -> Color)? = null

    override fun setPercent(percent: Double) {
        val value = iterator?.let { it(percent) }
        shape.fill = value?.jfxColor
    }

    override var stroke: ColorOrGradient?
        get() = (shape.stroke as javafx.scene.paint.Color?)?.d2vColor
        set(value) {
            (shape as? StateableElement)?.stateManager?.let {
                if (it.status == StateManagerStatus.RECORD) {
                    //Todo manage null color with alpha
                    start = (shape.stroke as? JfxColor)?.d2vColor
                    end = (shape.stroke as? JfxColor)?.d2vColor
                    it.addStateProperty(this)
                    iterator = interpolateRgb(start!!, end!!)
                    return
                }
            }

            shape.stroke = when (value) {
                null -> null
                is Color -> value.jfxColor
                is LinearGradient -> value.toLinearGradientJFX()
                else -> throw IllegalStateException("$value not managed")
            }
        }

    override var strokeWidth: Double?
        get() = shape.strokeWidth
        set(value) {
            if (value != null) shape.strokeWidth = value
        }

}


fun LinearGradient.toLinearGradientJFX(): JfxLinearGradient = JfxLinearGradient(
    x1, y1, x2, y2,
    false,
    CycleMethod.NO_CYCLE, colorStops.toStops()
)


// TODO : if r == null maybe proportionnal ?
fun RadialGradient.toRadialGradientJFX(): JfxRadialGradient  = JfxRadialGradient(.0, .0, cx, cy, r,
    false,
    CycleMethod.NO_CYCLE, colorStops.toStops())

private fun List<ColorStop>.toStops(): List<Stop>? =  map { Stop(it.percent, it.color.jfxColor) }

