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

    fun arcTo(fromX:Number, fromY:Number, toX:Number, toY:Number, radius:Number){
        val r = radius.toDouble()
        if (r < 0.0) throw IllegalArgumentException("Negative radius:" + radius)

        val x1 = fromX.toDouble()
        val y1 = fromY.toDouble()
        val x2 = toX.toDouble()
        val y2 = toY.toDouble()

        val x0 = this.x1?.toDouble() ?: .0
        val y0 = this.y1?.toDouble() ?: .0

        val x21 = x2 - x1
        val y21 = y2 - y1
        val x01 = x0 - x1
        val y01 = y0 - y1
        val l01_2 = x01 * x01 + y01 * y01

        with(this.x1){
            //path is empty, introduce private function?
            if(this == null){
                // Is this path empty? Move to (x1,y1).
                this@Path.x1 = x1
                this@Path.y1 = y1
                cmd += "M$x1,$y1"
            }
            // Or, is (x1,y1) coincident with (x0,y0)? Do nothing.
            else if (l01_2 <= epsilon){}

            // Or, are (x0,y0), (x1,y1) and (x2,y2) collinear?
            // Equivalently, is (x1,y1) coincident with (x2,y2)?
            // Or, is the radius zero? Line to (x1,y1).
            else if (Math.abs(y01 * x21 - y21 * x01) <= epsilon || r == .0) {
                this@Path.x1 = x1
                this@Path.y1 = y1
                cmd += "L$x1,$y1"
            }

            // Otherwise, draw an arc!
            else {
                val x20 = x2 - x0
                val y20 = y2 - y0
                val l21_2 = x21 * x21 + y21 * y21
                val l20_2 = x20 * x20 + y20 * y20
                val l21 = Math.sqrt(l21_2)
                val l01 = Math.sqrt(l01_2)
                val l = r * Math.tan((pi - Math.acos((l21_2 + l01_2 - l20_2) / (2 * l21 * l01))) / 2)
                val t01 = l / l01
                val t21 = l / l21;

                // If the start tangent is not coincident with (x0,y0), line to.
                if (Math.abs(t01 - 1) > epsilon) {
                    cmd += "L${x1 + t01 * x01},${y1 + t01 * y01}";
                }

                this@Path.x1 = x1 + t21 * x21
                this@Path.y1 = y1 + t21 * y21
                val yes = if (y01 * x20 > x01 * y20) 1 else 0
                cmd += "A$r,$r,0,0,$yes,${this@Path.x1},${this@Path.y1}"
            }
        }
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

    fun rect(x:Number, y:Number, w:Number, h:Number) {
        x0 = x
        x1 = x
        y0 = y
        y1 = y
        cmd += "M$x,${y}h${w}v${h}h${-w.toDouble()}Z"
    }


}
