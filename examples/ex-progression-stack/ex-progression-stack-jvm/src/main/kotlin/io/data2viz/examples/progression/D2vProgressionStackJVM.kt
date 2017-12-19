package io.data2viz.examples.progression

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage
import io.data2viz.viz.*



class D2vProgressionStackJVM : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            
            val totalCommon = modules.fold(0) {acc: Int, moduleState: ModuleState -> acc +moduleState.commonLOC}
            val totalJs = modules.fold(0) {acc: Int, moduleState: ModuleState -> acc +moduleState.jsLOC}
            val totalJFX = modules.fold(0) {acc: Int, moduleState: ModuleState -> acc +moduleState.JVMLOC}

            println("Total common:: $totalCommon")
            println("Total JS    :: $totalJs")
            println("Total JFX   :: $totalJFX")
            
            Application.launch(D2vProgressionStackJVM::class.java)
        }
    }


    override fun start(primaryStage: Stage?) {
        val root = Group()
        root.viz {
            progression()
        }

        primaryStage?.let {
            it.scene = (Scene(root, width + margins.hMargins, height + margins.vMargins))
            it.show()
        }
    }

}
