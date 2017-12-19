package io.data2viz.examples.streamGraph

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage
import io.data2viz.viz.*
import javafx.beans.value.ChangeListener
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox

class D2vStreamGraphJVM : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(D2vStreamGraphJVM::class.java)
        }
    }
    
    val chart = Group()

    override fun start(primaryStage: Stage?) {
        val root = VBox()

        renderChart()

        val form = getForm()
        root.children.add(form)
        root.children.add(chart)
        primaryStage?.let {
            it.scene = (Scene(root, 1400.0, 800.0))
            it.show()
            it.title = "JavaFx - data2viz - StreamGraph.kt"
        }
    }

    fun renderChart() {
        while (chart.children.size > 0){
            chart.children.removeAt(0)
        }
        chart.viz {
            streamGraph()
        }
        chart.prefHeight(height + margins.vMargins)
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
            selectionModel.selectedIndexProperty().addListener({ observable, oldValue, newValue ->
                vizConfig.curve = curveOptions[newValue.toInt()].second
                renderChart()
            })
            selectionModel.select(0)
        }
        
        grid.add(curveBox, 1,0)

        grid.add(Label("Offset"), 0,1)
        val offsetBox = ChoiceBox<String>().apply {
            items =  FXCollections.observableList(offsetOptions.map { it.first })
            selectionModel.selectedIndexProperty().addListener({ observable, oldValue, newValue ->
                vizConfig.offset = offsetOptions[newValue.toInt()].second
                renderChart()
            })
            selectionModel.select(4)
        }
        grid.add(offsetBox, 1,1)

        grid.add(Label("Order"), 0,2)
        val orderBox = ChoiceBox<String>().apply {
            items =  FXCollections.observableList(orderOptions.map { it.first })
            selectionModel.selectedIndexProperty().addListener({ observable, oldValue, newValue ->
                vizConfig.order = orderOptions[newValue.toInt()].second
                renderChart()
            })
            selectionModel.select(0)
        }
        grid.add(orderBox, 1,2)
        
        return grid
        
        
        
    }


}
