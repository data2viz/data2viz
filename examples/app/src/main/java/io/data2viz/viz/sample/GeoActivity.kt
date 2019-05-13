package io.data2viz.viz.sample

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import io.data2viz.examples.geo.*
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.viz.Viz
import io.data2viz.viz.VizView
import io.data2viz.viz.toView
import io.data2viz.viz.ui.FPSMeterView


class GeoActivity : AppCompatActivity() {


    val logTag = GeoActivity::class.java.simpleName

    val fileToResId = hashMapOf(
        "usstates.json" to R.raw.usstates,
        "world-110m.geojson" to R.raw.world110m,
        "world-110m-30percent.json" to R.raw.world110m30percent,
        "world-110m-50percent.json" to R.raw.world110m50percent,
        "world-110m-70percent.json" to R.raw.world110m70percent
    )

    lateinit var layoutViz: FrameLayout
    lateinit var buttonStartStop: Button
        lateinit var textFps: FPSMeterView
    lateinit var spinnerFiles: Spinner
    lateinit var spinnerProjections: Spinner

    var isAnimationStarted = true

    var viz: Viz? = null
    var view: VizView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "GeoActivity.onCreate")

        setContentView(R.layout.activity_geo)

        layoutViz = findViewById(R.id.layout_viz)
        buttonStartStop = findViewById(R.id.button_start_stop_animations)
        textFps = findViewById(R.id.text_fps)
        spinnerFiles = findViewById(R.id.spinner_files)
        spinnerProjections = findViewById(R.id.spinner_projections)

        buttonStartStop.setOnClickListener {

            if (isAnimationStarted) {
                view?.stopAnimations()
            } else {
                view?.startAnimations()

            }

            isAnimationStarted = !isAnimationStarted
        }

        spinnerFiles.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allFiles)
        spinnerProjections.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allProjectionsNames.toList())

        spinnerFiles.setSelection(defaultFileIndex)
        spinnerProjections.setSelection(defaultProjectionIndex)

        Handler().postDelayed({
            val listener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    onSelectionChanged()
                }

            }
            spinnerFiles.onItemSelectedListener = listener
            spinnerProjections.onItemSelectedListener = listener

            onSelectionChanged()


        }, 1000)


//        onSelectionChanged()


    }

    private fun onSelectionChanged() {


        layoutViz.removeAllViews()

        view?.stopAnimations()


        val projection = allProjectionsNames[spinnerProjections.selectedItemPosition]
        val file = allFiles[spinnerFiles.selectedItemPosition]
        Log.d(logTag, "onSelectionChanged projection=$projection file=$file")

        val resId = fileToResId[file]
        val world = resources.openRawResource(resId!!)
            .reader().readText().toGeoJsonObject()

        viz = geoViz(world, projection)
        view = viz!!.toView(this)

        textFps.vizView = view

        layoutViz.addView(view)

        if (isAnimationStarted) {

            view!!.startAnimations()
        }

    }


    override fun onStop() {
        super.onStop()
        Log.d(logTag, "onStop")
        view?.stopAnimations()
        textFps.vizView = null
    }


}