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
import io.data2viz.color.Colors
import io.data2viz.timer.Timer
import io.data2viz.timer.timer
import io.data2viz.viz.CircleNode
import io.data2viz.viz.VizView
import io.data2viz.viz.toView
import io.data2viz.viz.viz


class BounceActivity : AppCompatActivity() {

    lateinit private var view: VizView
    lateinit private var timer: Timer
    lateinit var circle: CircleNode
    val viz = viz {

        width = 100.0
        height = 100.0


        circle = circle {
            x = 0.0
            y = 50.0
            fill = Colors.Web.red
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = viz.toView(this)
        setContentView(view)
        timer = timer {
            if (circle.x > 100.0)
                circle.x = .0
            circle.x = circle.x + 1
            view.invalidate()
        }
    }

    override fun onStop() {
        super.onStop()
        timer.stop()
    }
}
