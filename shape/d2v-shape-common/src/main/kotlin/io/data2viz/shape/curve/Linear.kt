package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve


class Linear(override val context: PathAdapter): Curve {

    private var point = 0
//    private var line: Int = -1

    override fun areaStart() {
//        line = 0
    }
    override fun areaEnd()   {
//        line = -1
    }
    override fun lineStart() {
        point = 0
    }

    override fun lineEnd() {
//        if (line > 0 || (line != 0 && point == 1)) {
        if ( point == 1) {
            context.closePath()
        }
//        if(line != -1)
//            line = 1 - line
    }

    override fun point(x: Double, y: Double) {

        if (point == 0) {
            point = 1
//            if (line > 0) {
//                context.lineTo(x, y)
//            } else {
                context.moveTo(x, y)
//            }
            return
        }
        if (point == 1) {
            point = 2
        }
        context.lineTo(x,y)
    }
}

