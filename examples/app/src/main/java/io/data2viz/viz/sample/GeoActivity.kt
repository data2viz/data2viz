package io.data2viz.viz.sample

import android.animation.TimeAnimator
import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.data2viz.color.colors
import io.data2viz.geo.path.GeoPath
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.orthographic
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.viz.AndroidCanvasRenderer
import io.data2viz.viz.PathNode
import io.data2viz.viz.viz


class GeoActivity : AppCompatActivity() {

    lateinit var geoView: GeoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("GeoActivity.onCreate")

        val world = getResources().openRawResource(R.raw.world110m30percent)
                .reader().readText().toGeoJsonObject()


        geoView = GeoView(this, world)
        setContentView(geoView)
    }

    override fun onStop() {
        super.onStop()
        println("onStop")
        geoView.stopAnimation()
    }

}

class GeoView(context: Context, val world: GeoJsonObject) : View(context) {

    val renderer = AndroidCanvasRenderer(context, Canvas())

    val timeAnimator = TimeAnimator().apply {
        setTimeListener { _, _, _ ->
            rotate()
        }

    }

    val geoViz = viz {
        height = 600.0
        width = 800.0
    }

    private var pathOuter: PathNode

    private var geoPathOuter: GeoPath

    init {

        // OUTER GLOBE
        val projectionOuter = orthographic {
            translate = doubleArrayOf(400.0, 300.0)
            scale = 250.0
        }
        pathOuter = PathNode().apply {
            stroke = colors.black
            strokeWidth = 2.0
            fill = colors.whitesmoke
        }
        geoPathOuter = geoPath(projectionOuter, pathOuter)
        geoPathOuter.path(world)

        geoViz.add(pathOuter)
        timeAnimator.start()
    }

    override fun onDraw(canvas: Canvas) {
//        println("GeoView.onDraw")
        renderer.canvas = canvas
        renderer.render(geoViz)

//        println("Viz elements:: " + (geoViz.activeLayer.children[0] as PathNode).path.commands.take(20).joinToString())
    }

    fun rotate(){
        var time = System.currentTimeMillis()
        val rotate = geoPathOuter.projection.rotate
        rotate[0] += .5
        rotate[1] = -10.0
        pathOuter.clearPath()
        geoPathOuter.path(world)
        geoPathOuter.projection.rotate = rotate

        time -= System.currentTimeMillis()
//        println("Update path in ${-time} ms")
        invalidate()
    }

    fun stopAnimation() {
        timeAnimator.pause()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        updateScale()
    }

    fun updateScale() {
        val scale = (width / geoViz.width).toFloat()

        println("updateScale:: $scale current width::$width")
        renderer.scale = 1.3f

    }

}