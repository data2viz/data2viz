
import io.data2viz.viz.ParentElement
import io.data2viz.viz.selectOrCreateSvg


import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date


fun main(args: Array<String>) {
    
    
    val svg = selectOrCreateSvg().apply { 
        setAttribute("width", widthHeight.toString())
        setAttribute("height", widthHeight.toString())
    }
    svg.append((root as ParentElement).domElement)
    fun animate() {
        window.requestAnimationFrame {
            loop(Date.now())
            animate()
        }
    }
    animate()
}

actual fun random(): Double  = kotlin.js.Math.random()
