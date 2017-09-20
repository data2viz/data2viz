package curve

import io.data2viz.path.PathAdapter
import io.data2viz.path.svgPath

interface Curve {
    val context: PathAdapter
    fun areaStart()
    fun areaEnd()
    fun lineStart()
    fun lineEnd()
    fun point(x: Number, y: Number)
}


class Linear(override val context: PathAdapter): Curve {

    private var status = 0
    private var lineStatus: Int = -1

    override fun areaStart() {
        lineStatus = 0
    }

    override fun areaEnd() {
        lineStatus = -1
    }

    override fun lineStart() {
        status = 0
    }

    override fun lineEnd() {
        if (lineStatus > 0 || (lineStatus != 0 && status == 1)) {
            context.closePath()
        }
        lineStatus = 1 - lineStatus
    }

    override fun point(x: Number, y: Number) {

        if (status == 0) {
            status = 1
            if (lineStatus > 0) {
                context.lineTo(x, y)
            } else {
                context.moveTo(x, y)
            }
            return
        }
        if (status == 1) {
            status = 2
        }

        context.lineTo(x,y)
    }
}

