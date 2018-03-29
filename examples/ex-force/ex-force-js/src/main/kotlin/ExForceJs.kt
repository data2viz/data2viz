
import io.data2viz.viz.ParentElement
import io.data2viz.viz.selectOrCreateSvg

fun main(args: Array<String>) {
    selectOrCreateSvg().apply { 
        setAttribute("width", width.toString())
        setAttribute("height", height.toString())
        append((root as ParentElement).domElement)
    }
}
