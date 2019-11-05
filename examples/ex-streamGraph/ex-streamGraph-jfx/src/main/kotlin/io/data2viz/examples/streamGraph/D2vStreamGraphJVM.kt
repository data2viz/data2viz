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

package io.data2viz.examples.streamGraph

import io.data2viz.viz.JFxVizRenderer
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class D2vStreamGraphJVM : Application() {

    val canvas = Canvas(width, height)
    val viz = streamGraph()
    val renderer = JFxVizRenderer(canvas, viz)

    init {
        viz.renderer = renderer
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(D2vStreamGraphJVM::class.java)
        }
    }

    fun renderChart() {
        viz.render()
    }


    override fun start(stage: Stage?) {

        println("Building viz")
        val root = VBox()

        stage?.let {
            it.scene = (Scene(root, width, height))
            it.show()
            root.children.add(canvas)
            renderChart()
        }

        val form = getForm()
        root.children.add(form)
    }


    private fun getForm(): GridPane {
        val grid = GridPane().apply {
            hgap = 10.0
            vgap = 10.0
            padding = Insets(25.0, 25.0, 25.0, 25.0)
        }
        
        grid.add(Label("Curve type"), 0,0)
        val curveBox = ChoiceBox<String>().apply {
            items =  FXCollections.observableList(curveOptions.map { it.first })
            selectionModel.selectedIndexProperty().addListener({ _, _, newValue ->
                vizConfig.curve = curveOptions[newValue.toInt()].second
                renderChart()
            })
            selectionModel.select(0)
        }
        
        grid.add(curveBox, 1,0)

        grid.add(Label("Offset"), 0,1)
        val offsetBox = ChoiceBox<String>().apply {
            items =  FXCollections.observableList(offsetOptions.map { it.first })
            selectionModel.selectedIndexProperty().addListener({ _, _, newValue ->
                vizConfig.offset = offsetOptions[newValue.toInt()].second
                renderChart()
            })
            selectionModel.select(4)
        }
        grid.add(offsetBox, 1,1)

        grid.add(Label("Order"), 0,2)
        val orderBox = ChoiceBox<String>().apply {
            items =  FXCollections.observableList(orderOptions.map { it.first })
            selectionModel.selectedIndexProperty().addListener({ _, _, newValue ->
                vizConfig.order = orderOptions[newValue.toInt()].second
                renderChart()
            })
            selectionModel.select(0)
        }
        grid.add(orderBox, 1,2)
        
        return grid
    }


}
