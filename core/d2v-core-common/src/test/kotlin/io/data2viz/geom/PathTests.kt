@file:Suppress("unused")

package io.data2viz.geom

import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.math.PI
import kotlin.test.Ignore
import kotlin.test.Test

fun moveTo(x:Double) = "M$x"


@Suppress("FunctionName", "NonAsciiCharacters")
class PathTests : TestBase() {

    fun path(): PathGeom = PathGeom()

    private fun PathGeom.moveTo(x:Int, y:Int) { moveTo(x.toDouble(), y.toDouble()) }
    private fun PathGeom.lineTo(x:Int, y:Int) { lineTo(x.toDouble(), y.toDouble()) }
    private fun PathGeom.bezierCurveTo(x1: Int, y1: Int, x2: Int, y2: Int, x: Int, y: Int) { bezierCurveTo(x1.toDouble(), y1.toDouble(), x2.toDouble(), y2.toDouble(), x.toDouble(), y.toDouble()  )}
    private fun PathGeom.arcTo(fromX:Number, fromY:Number, toX:Number, toY:Number, radius:Number) { arcTo(fromX.toDouble(), fromY.toDouble(), toX.toDouble(), toY.toDouble(), radius.toDouble())}
    private fun PathGeom.arc(centerX:Int, centerY:Int, radius:Int, startAngle:Number, endAngle:Number, counterClockWise:Boolean = false) { arc(centerX.toDouble(), centerY.toDouble(), radius.toDouble(), startAngle.toDouble(), endAngle.toDouble(), counterClockWise) }
    private fun PathGeom.quadraticCurveTo(x1: Int, y1: Int, x: Int, y: Int)  { this.quadraticCurveTo(x1.toDouble(), y1.toDouble(), x.toDouble(), y.toDouble())}


    @Test
    fun test_round_path() {
        "M3.061616997868383e-15,0A50,50,0,0,1,50,50".round() shouldBe "M0,0A50,50,0,0,1,50,50"
    }

    @Test
    fun pathMoveToAppendMCommand() {
        with(path()) {
            moveTo(150, 50)
            println(svgPath)
            svgPath.round() shouldBe "M150,50"
            lineTo(200, 100)
            svgPath.round() shouldBe "M150,50L200,100"
            moveTo(100, 50)
            svgPath.round() shouldBe "M150,50L200,100M100,50"
        }
    }

    @Test
    fun path_closePath_appends_Z_command() {
        with(path()) {
            moveTo(150, 50)
            svgPath.round() shouldBe "M150,50"
            closePath()
            svgPath.round() shouldBe "M150,50Z"
        }
    }

    @Test
    fun path_closePath_does_nothing_if_empty() {
        with(path()) {
            svgPath.round() shouldBe ""
            closePath()
            svgPath.round() shouldBe ""
        }
    }

    @Test
    fun path_lineTo_appends_L_command() {
        with(path()) {
            moveTo(150, 50)
            svgPath.round() shouldBe "M150,50"
            lineTo(200, 100)
            svgPath.round() shouldBe "M150,50L200,100"
            lineTo(100, 50)
            svgPath.round() shouldBe "M150,50L200,100L100,50"
        }
    }

    @Test
    fun path_quadraticCurveTo_appends_Q_command() {
        with(path()) {
            moveTo(150, 50)
            quadraticCurveTo(100, 50, 200, 100)
            svgPath.round() shouldBe "M150,50Q100,50,200,100"
        }
    }

    @Test
    fun path_bezierCurveTo_appends_C_command() {
        with(path()) {
            moveTo(150, 50)
            bezierCurveTo(100, 50, 0, 24, 200, 100)
            svgPath.round() shouldBe "M150,50C100,50,0,24,200,100"
        }
    }

    @Test
    fun path_arc_throws_an_error_if_radius_is_negative() {
        with(path()) {
            moveTo(150, 50)
            shouldThrow<IllegalArgumentException> { arc(100, 100, -50, 0, PI / 2) }
        }
    }

    @Test
    fun path_arc_may_append_only_an_M_command_if_the_radius_is_zero() {
        with(path()) {
            arc(100, 100, 0, 0, PI / 2)
            svgPath.round() shouldBe "M100,100"
        }
    }

    @Test
    fun path_arc_may_append_only_an_L_command_if_previous_path() {
        with(path()) {
            moveTo(0, 0)
            arc(100, 100, 0, 0, PI / 2)
            svgPath.round() shouldBe "M0,0L100,100"
        }
    }

    @Test
    fun path_arc_may_append_only_an_M_command_if_the_angle_is_zero() {
        with(path()) {
            arc(100, 100, 0, 0, 0)
            svgPath.round() shouldBe "M100,100"
        }
    }

    @Test
    fun path_arc_may_append_only_an_M_command_if_the_angle_is_near_zero() {
        with(path()) {
            arc(100, 100, 0, 0, 1e-16)
            svgPath.round() shouldBe "M100,100"
        }
    }


    @Test
    fun path_arc_may_append_only_an_M_command_if_the_path_was_empty() {
        with(path()) {
            arc(100, 100, 50, 0, PI * 2)
            svgPath.round() shouldBe "M150,100A50,50,0,1,1,50,100A50,50,0,1,1,150,100"
        }

        with(path()) {
            arc(0, 50, 50, -PI / 2, 0)
            svgPath.round() shouldBe "M0,0A50,50,0,0,1,50,50"
        }
    }

    @Test
    fun path_arc_may_append_an_L_command_if_the_arc_doesn_t_start_at_the_current_point() {
        with(path()) {
            moveTo(100, 100)
            arc(100, 100, 50, 0, PI * 2)
            svgPath.round() shouldBe "M100,100L150,100A50,50,0,1,1,50,100A50,50,0,1,1,150,100"
        }
    }

    @Test
    fun path_arc_appends_a_single_A_command_if_the_angle_is_less_than_π() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, PI / 2)
            svgPath.round() shouldBe "M150,100A50,50,0,0,1,100,150"
        }
    }

    @Test
    fun path_arc_appends_a_single_A_command_if_the_angle_is_less_than_τ() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, PI * 1)
            svgPath.round() shouldBe "M150,100A50,50,0,1,1,50,100"
        }
    }

    @Test
    fun path_arc_appends_two_A_commands_if_the_angle_is_greater_than_τ() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, PI * 2)
            svgPath.round() shouldBe "M150,100A50,50,0,1,1,50,100A50,50,0,1,1,150,100"
        }
    }

    @Test
    fun path_arc_appends_draws_a_small_clockwise_arc2() {
        with(path()) {
            moveTo(100, 50)
            arc(100, 100, 50, -PI / 2, 0, false)
            svgPath.round() shouldBe "M100,50A50,50,0,0,1,150,100"
        }
    }

    @Test
    fun path_arc_appends_draws_a_small_clockwise_arc() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, PI / 2, false)
            svgPath.round() shouldBe "M150,100A50,50,0,0,1,100,150"
        }
    }

    @Test
    fun path_arc_draws_an_anticlockwise_circle() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, 1e-16, true)
            svgPath.round() shouldBe "M150,100A50,50,0,1,0,50,100A50,50,0,1,0,150,100"
        }
    }

    @Test
    fun path_arc_0_epsilon_angle_does_nothing() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, 1e-16, false)
            svgPath.round() shouldBe "M150,100"
        }
    }

    @Test
    fun path_arc_0_minus_epsilon_angle_draws_a_clockwise_circle() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, -1e-16, false)
            svgPath.round() shouldBe "M150,100A50,50,0,1,1,50,100A50,50,0,1,1,150,100"
        }
    }

    @Test
    fun path_arc_minus_epsilon_ccw_angle_does_nothing() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, -1e-16, true)
            svgPath.round() shouldBe "M150,100"
        }
    }

    @Test
    fun path_arc_0_tau_draws_an_anticlockwise_circle() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, 2 * PI, true)
            svgPath.round() shouldBe "M150,100A50,50,0,1,0,50,100A50,50,0,1,0,150,100"
        }
    }

    @Test
    fun path_arc_0_tau_false_draws_a_clockwise_circle() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, 2 * PI, false)
            svgPath.round() shouldBe "M150,100A50,50,0,1,1,50,100A50,50,0,1,1,150,100"
        }
    }

    @Test
    fun path_arc_() {
        with(path()) {
            moveTo(150, 100)
            svgPath.round() shouldBe "M150,100"
        }
    }

    @Test
    fun path_arc_x_y_0_13πon2_false__draws_a_clockwise_circle() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, 13 * PI / 2, false)
            svgPath.round() shouldBe "M150,100A50,50,0,1,1,50,100A50,50,0,1,1,150,100"
        }
    }

    @Test
    fun path_arc_x_y_13πon2_0_false__draws_a_big_clockwise_arc() {
        with(path()) {
            moveTo(100, 150)
            arc(100, 100, 50, 13 * PI / 2, 0, false)
            svgPath.round() shouldBe "M100,150A50,50,0,1,1,150,100"
        }
    }

    @Test
    fun path_arc_x_y_πon2_0_false__draws_a_big_clockwise_arc() {
        with(path()) {
            moveTo(100, 150)
            arc(100, 100, 50, PI / 2, 0, false)
            svgPath.round() shouldBe "M100,150A50,50,0,1,1,150,100"
        }
    }

    @Test
    fun path_arc_x_y_3πon2_0_false__draws_a_small_clockwise_arc() {
        with(path()) {
            moveTo(100, 50)
            arc(100, 100, 50, 3 * PI / 2, 0, false)
            svgPath.round() shouldBe "M100,50A50,50,0,0,1,150,100"
        }
    }

    @Test
    fun path_arc_x_y_15πon2_0_false__draws_a_small_clockwise_arc() {
        with(path()) {
            moveTo(100, 50)
            arc(100, 100, 50, 15 * PI / 2, 0, false)
            svgPath.round() shouldBe "M100,50A50,50,0,0,1,150,100"
        }
    }

    @Test
    fun path_arc_x_y_0_πon2_true__draws_a_big_anticlockwise_arc() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, PI / 2, true)
            svgPath.round() shouldBe "M150,100A50,50,0,1,0,100,150"
        }
    }

    @Test
    fun path_arc_x_y_minusπon2_0_true__draws_a_big_anticlockwise_arc() {
        with(path()) {
            moveTo(100, 50)
            arc(100, 100, 50, -PI / 2, 0, true)
            svgPath.round() shouldBe "M100,50A50,50,0,1,0,150,100"
        }
    }

    @Test
    fun path_arc_x_y_minus13πon2_0_true_draws_a_big_anticlockwise_arc() {
        with(path()) {
            moveTo(100, 50)
            arc(100, 100, 50, -13 * PI / 2, 0, true)
            svgPath.round() shouldBe "M100,50A50,50,0,1,0,150,100"
        }
    }

    @Test
    fun path_arc_x_y_minus13πon2_0_false__draws_a_big_anticlockwise_arc() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, -13 * PI / 2, false)
            svgPath.round() shouldBe "M150,100A50,50,0,1,1,100,50"
        }
    }

    @Test
    fun path_arc_x_y_0_13πon2_true__draws_a_big_anticlockwise_arc() {
        with(path()) {
            moveTo(150, 100)
            arc(100, 100, 50, 0, 13 * PI / 2, true)
            svgPath.round() shouldBe "M150,100A50,50,0,1,0,100,150"
        }
    }

    @Test
    fun path_arc_x_y_πon2_0_true__draws_a_small_anticlockwise_arc() {
        with(path()) {
            moveTo(100, 150)
            arc(100, 100, 50, PI / 2, 0, true)
            svgPath.round() shouldBe "M100,150A50,50,0,0,0,150,100"
        }
    }

    @Test
    fun path_arc_x_y_3πon2_0_true__draws_a_big_anticlockwise_arc() {
        with(path()) {
            moveTo(100, 50)
            arc(100, 100, 50, 3 * PI / 2, 0, true)
            svgPath.round() shouldBe "M100,50A50,50,0,1,0,150,100"
        }
    }

    @Test
    fun path_arcTo_with_negative_radius_throws_error() {
        shouldThrow<IllegalArgumentException> { path().arcTo(1, 1, 1, 1, -1) }
    }


    @Test
    fun path_arcTo_appends_an_M_command_if_the_path_was_empty() {
        with(path()) {
            arcTo(270, 39, 163, 100, 53)
            svgPath.round() shouldBe "M270,39"
        }
    }

    @Test
    fun path_arcTo_does_nothing_if_the_previous_point_was_x1_y1() {
        with(path()) {
            moveTo(270, 39); arcTo(270, 39, 163, 100, 53)
            svgPath.round() shouldBe "M270,39"
        }
    }

    @Test
    fun path_arcTo_appends_an_L_command_if_the_previous_point_x1_y1_and_x2_y2_are_collinear() {
        with(path()) {
            moveTo(100, 50); arcTo(101, 51, 102, 52, 10)
            svgPath.round() shouldBe "M100,50L101,51"
        }
    }

    @Test
    fun path_arcTo_appends_an_L_command_if_x1_y1_and_x2_y2_are_coincident() {
        with(path()) {
            moveTo(100, 50); arcTo(101, 51, 101, 51, 10)
            svgPath.round() shouldBe "M100,50L101,51"
        }
    }

    @Test
    fun path_arcTo_appends_an_L_command_if_the_radius_is_zero() {
        with(path()) {
            moveTo(270, 182)
            arcTo(270, 39, 163, 100, 0)
            svgPath.round() shouldBe "M270,182L270,39"
        }
    }

    @Test
    fun path_arcTo_appends_L_and_A_commands_if_the_arc_does_not_start_at_the_current_point() {
        with(path()) {
            moveTo(270, 182)
            arcTo(270, 39, 163, 100, 53)
            svgPath.round() shouldBe "M270,182L270,130.222686A53,53,0,0,0,190.750991,84.179342"
        }
        with(path()) {
            moveTo(270, 182)
            arcTo(270, 39, 363, 100, 53)
            svgPath.round() shouldBe "M270,182L270,137.147168A53,53,0,0,1,352.068382,92.829799"
        }
    }

    @Test
    fun path_arcTo_appends_only_an_A_command_if_the_arc_starts_at_the_current_point() {
        with(path()) {
            moveTo(100, 100)
            arcTo(200, 100, 200, 200, 100)
            svgPath.round() shouldBe "M100,100A100,100,0,0,1,200,200"
        }
    }

    @Test
    fun path_arcTo_sets_the_last_point_to_be_the_end_tangent_of_the_arc() {
        with(path()) {
            moveTo(100, 100)
            arcTo(200, 100, 200, 200, 50); arc(150, 150, 50, 0, PI)
            svgPath.round() shouldBe "M100,100L150,100A50,50,0,0,1,200,150A50,50,0,1,1,100,150"
        }
    }

    @Suppress("DEPRECATION")
    @Test
    fun path_rect_appends_M_h_v_h_and_Z_commands() {
        with(path()){
            moveTo(150,100)
            rect(100.0,200.0, 50.0,25.0)
            svgPath.round() shouldBe "M150,100M100,200h50v25h-50Z"
        }
    }

    @Test
    @Ignore
    fun closePathShouldComeBackToFirstPoint() {
        var lastCommand: ClosePath
        with(path()){
            moveTo(10.0, 10.0)
            lineTo(20.0, 20.0)
            closePath()
            lastCommand = commands.last() as ClosePath
        }
        lastCommand.x shouldBe 10.0
        lastCommand.y shouldBe 10.0
    }

}
