package io.data2viz.viz

import javafx.scene.Scene
import javafx.stage.Stage
import org.junit.Test
import org.testfx.api.FxAssert.verifyThat
import org.testfx.framework.junit.ApplicationTest
import javafx.application.Application
import javafx.scene.layout.StackPane
import javafx.scene.control.Button
import org.testfx.matcher.base.NodeMatchers.hasText

class ClickApplicationTest : ApplicationTest() {
	override fun start(stage: Stage) {
		val sceneRoot = ClickApplication.ClickPane()
		val scene = Scene(sceneRoot, 100.0, 100.0)
		stage.scene = scene
		stage.show()
	}

	@Test
	fun should_contain_button() {
		// expect:
		verifyThat(".button", hasText("click me!"))
	}

	@Test
	fun should_click_on_button() {
		// when:
		clickOn(".button")

		// then:
		verifyThat(".button", hasText("clicked!"))
	}
}


class ClickApplication : Application() {
	// application for acceptance tests.
	override fun start(stage: Stage) {
		val sceneRoot = ClickPane()
		val scene = Scene(sceneRoot, 100.0, 100.0)
		stage.scene = scene
		stage.show()
	}

	// scene object for unit tests
	class ClickPane : StackPane() {
		init {
			val button = Button("click me!")
			button.setOnAction({ actionEvent -> button.setText("clicked!") })
			children.add(button)
		}
	}
}
