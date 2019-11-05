/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.viz.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        R.id.button_show_bounce.showActivity(BounceActivity::class.java)
        R.id.button_show_geo.showActivity(GeoActivity::class.java)
        R.id.button_show_chord.showActivity(ChordActivity::class.java)
        R.id.button_show_force.showActivity(ForceActivity::class.java)
        R.id.button_show_line_of_sight.showActivity(LineOfSightActivity::class.java)
        R.id.button_show_sankey.showActivity(SankeyActivity::class.java)
        R.id.button_show_events.showActivity(EventsActivity::class.java)

    }

    fun <T : AppCompatActivity> Int.showActivity(activity: Class<T>) {
        findViewById<Button>(this)
                .setOnClickListener {
                    startActivity(Intent(this@MainActivity, activity))
                }
    }
}
