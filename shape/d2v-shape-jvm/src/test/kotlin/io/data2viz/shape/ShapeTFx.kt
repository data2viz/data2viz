package io.data2viz.shape

import javafx.application.Application
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.scene.text.FontWeight.BOLD
import tornadofx.*
import tornadofx.Stylesheet.Companion.box
import tornadofx.Stylesheet.Companion.button
import tornadofx.Stylesheet.Companion.textInput


fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}

class MyApp: App(MyView::class)

class MyView: View() {
    override val root = VBox()

    init {
        root.apply {
            this += Button("Press Me")
            this += Label("Waiting")
            this += Label("Yo")
        }
    }
}

class Styles : Stylesheet() {
    companion object {
        // Define css classes
        val heading by cssclass()

        // Define colors
        val mainColor = c("#bdbd22")
    }

    init {
        heading {
            textFill = mainColor
            fontSize = 20.px
            fontWeight = BOLD
        }

        button {
            padding = box(10.px, 20.px)
            fontWeight = BOLD
        }

        val flat = mixin {
            backgroundInsets += box(0.px)
            borderColor += box(Color.DARKGRAY)
        }

        s(button, textInput) {
            +flat
        }
    }
}
