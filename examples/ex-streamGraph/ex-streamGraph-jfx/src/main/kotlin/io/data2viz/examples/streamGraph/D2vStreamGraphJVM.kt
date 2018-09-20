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
