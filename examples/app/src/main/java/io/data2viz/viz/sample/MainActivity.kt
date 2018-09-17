package io.data2viz.viz.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import io.data2viz.logging.KotlinLogging

class MainActivity : AppCompatActivity() {

    val log = KotlinLogging.logger {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.debug { "onCreate" }
        setContentView(R.layout.activity_main)

//        R.id.button_show_bounce.showActivity(BounceActivity::class.java)
        R.id.button_show_geo.showActivity(GeoActivity::class.java)
        R.id.button_show_chord.showActivity(ChordActivity::class.java)
        R.id.button_show_force.showActivity(ForceActivity::class.java)
        R.id.button_show_line_of_sight.showActivity(LineOfSightActivity::class.java)
        R.id.button_show_sankey.showActivity(SankeyActivity::class.java)

    }

    fun <T : AppCompatActivity> Int.showActivity(activity: Class<T>) {
        findViewById<Button>(this)
                .setOnClickListener {
                    startActivity(Intent(this@MainActivity, activity))
                }
    }
}
