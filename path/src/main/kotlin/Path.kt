package io.data2viz.path

import kotlin.js.Math
import kotlin.js.Math.abs
import kotlin.js.Math.cos
import kotlin.js.Math.sin


fun path() = Path()

val pi = Math.PI
val tau = 2 * pi
val epsilon = 1e-6
val tauEpsilon = tau - epsilon

class Path() {

    var x0:Number = 0
    var y0:Number = 0
    var x1:Number? = null
    var y1:Number? = null

    var cmd:String = ""

    fun moveTo(x:Number, y:Number) {
        x0 = x
        y0 = y
        x1 = x
        y1 = y
        cmd += "M$x,$y"
    }

    fun lineTo(x: Int, y: Int) {
        x1 = x
        y1 = y
        cmd += "L$x,$y"

    }

    fun closePath() {
        if(x1 != null){
            x1 = x0
            y1 = y0
            cmd += "Z"
        }
    }

    fun quadraticCurveTo(x1: Number, y1: Number, x: Number, y: Number) {
        this.x1 = x
        this.y1 = y
        cmd += "Q$x1,$y1,$x,$y"
    }

    fun bezierCurveTo(x1: Number, y1: Number,x2: Number, y2: Number, x: Number, y: Number) {
        this.x1 = x
        this.y1 = y
        cmd += "C$x1,$y1,$x2,$y2,$x,$y"
    }


    /**
     *
     * @see https://www.w3.org/TR/2dcontext/#dom-context-2d-arc
     */
    fun arc(centerX:Number, centerY:Number, radius:Number, startAngle:Number, endAngle:Number, counterClockWise:Boolean = false){
        val r = radius.toDouble()
        if (r < 0.0) throw IllegalArgumentException("Negative radius:" + radius)

        val a0 = startAngle.toDouble()
        val a1 = endAngle.toDouble()
        val x = centerX.toDouble()
        val y = centerY.toDouble()

        val dx = r * cos(a0)
        val dy = r * sin(a0)
        val x0 = x + dx
        val y0 = y + dy
        val cw = if (counterClockWise) 0 else 1
//        var cw = pow(1.toDouble(),(counterClockWise.toDouble()))
        var da = if(counterClockWise) a0 - a1 else a1 - a0


        with(x1){

            //path is empty, introduce private function?
            if(this == null)
                cmd += "M$x0,$y0"

            else if (abs(this.toDouble() - x0) > epsilon || abs(y1!!.toDouble() - y0) > epsilon){
                cmd += "L$x0,$y0"
            }
        }

        if (r < epsilon) return

        if (da < 0) da = da % tau + tau

        //complete circle
        if (da > tauEpsilon) {
            x1 = x0
            y1 = y0
            cmd += "A$r,$r,0,1,$cw,${x - dx},${y - dy}A$r,$r,0,1,$cw,$x0,$y0";
        }

        // Is this arc non-empty? Draw an arc!
        else if (da > epsilon) {
            x1 = x + r * cos(a1)
            y1 = y + r * sin(a1)
            cmd += "A$r,$r,0,${if (da >= pi) 1 else 0},$cw,$x1,$y1";
        }
    }

}
