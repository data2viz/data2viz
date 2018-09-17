package io.data2viz.viz.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import io.data2viz.logging.KotlinLogging
import io.data2viz.timer.*


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

        log.debug { "now:: ${now().toInt()} ms" }

        val t1 = testSetTimeout(1000) { log.debug { "t1.timeout(1000.0) ${now().toInt()} ms" } }
        val t2 = testSetTimeout(1000) { log.debug { "t2.timeout(1000.0) ${now().toInt()} ms" } }
        val t3 = testSetTimeout(2000) { log.debug { "t3.timeout(2000.0) ${now().toInt()} ms" } }

        val i1 = testSetInterval(1000) { log.debug { "i1.interval(1000.0) ${now().toInt()} ms" } }
        val i2 = testSetInterval(1000) { log.debug { "i2.interval(1000.0) ${now().toInt()} ms" } }
        val i3 = testSetInterval(1000) { log.debug { "i3.interval(1000.0) ${now().toInt()} ms" } }

        testStopTimeout(t2)

        testStopInterval(i1)
        testSetTimeout(3000) {
            log.debug { "testStopInterval(i2)" }
            testStopInterval(i2)
        }
        testSetTimeout(5500) {
            log.debug { "testStopInterval(i3)" }
            testStopInterval(i3)
        }

    }

    fun <T : AppCompatActivity> Int.showActivity(activity: Class<T>) {
        findViewById<Button>(this)
                .setOnClickListener {
                    startActivity(Intent(this@MainActivity, activity))
                }
    }
}
