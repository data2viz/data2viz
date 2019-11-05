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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import io.data2viz.examples.events.*
import io.data2viz.viz.*


class EventsActivity : AppCompatActivity() {

    lateinit var button: Button

    @ExperimentalKEvent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chordViz = eventsViz()


        button = Button(this)
        toggleEventsState(chordViz, button)
        button.setOnClickListener() {
            toggleEventsState(chordViz, button)
        }


        val vizView = chordViz
            .toView(this)


        val linearLayout = LinearLayout(this)

        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(button)
        linearLayout.addView(vizView)

        setContentView(linearLayout)
    }

    @ExperimentalKEvent
    private fun toggleEventsState(
        viz: Viz,
        button: Button
    ) {
        if (isEventsAdded) {
			removeEvents()
            button.text = addEventsText
        } else {
            viz.addEvents()
            button.text = removeEventsText
        }
    }
}


