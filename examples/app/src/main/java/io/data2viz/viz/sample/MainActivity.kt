package io.data2viz.viz.sample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
