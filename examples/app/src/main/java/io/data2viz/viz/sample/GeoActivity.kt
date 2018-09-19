package io.data2viz.viz.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.data2viz.color.colors
import io.data2viz.examples.geo.geoViz
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.orthographic
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.viz.*
import kotlin.math.min


class GeoActivity : AppCompatActivity() {

    lateinit var view: VizView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("GeoActivity.onCreate")

        val world = getResources().openRawResource(R.raw.world110m30percent)
            .reader().readText().toGeoJsonObject()

        view = geoViz(world).toView(this)
        setContentView(view)
        view.startAnimations()
    }

    override fun onStop() {
        super.onStop()
        println("onStop")
        view.stopAnimations()
    }

}