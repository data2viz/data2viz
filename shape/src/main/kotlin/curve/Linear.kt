package curve

import io.data2viz.path.PathAdapter
import io.data2viz.path.svgPath


class Linear(val context: PathAdapter) {

    private var status = 0
    private var lineStatus: Int = -1

    fun areaStart() {
        lineStatus = 0
    }

    fun areaEnd() {
        lineStatus = -1
    }

    fun lineStart() {
        status = 0
    }

    fun lineEnd() {
        if (lineStatus > 0 || (lineStatus != 0 && status == 1)) {
            context.closePath()
        }
        lineStatus = 1 - lineStatus
    }

    fun point(x: Number, y: Number) {

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
