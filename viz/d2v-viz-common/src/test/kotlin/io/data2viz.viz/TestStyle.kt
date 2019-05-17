package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.test.*
import kotlin.test.Test

class TestStyle: TestBase(){

    @Test
    fun defaultTextColorIsBlack(){
        var node:Node? = null
        viz {
            group {
                node = text {
                }
            }
        }
        node?.textColor shouldBe Colors.Web.black
    }

    @Test
    fun vizTextColor(){
        var node:Node? = null
        viz {
            textColor = Colors.Web.red
            group {
                node = text {
                }
            }
        }
        node?.textColor shouldBe Colors.Web.red
    }

    @Test
    fun groupTextColor(){
        var node:Node? = null
        viz {
            group {
                textColor = Colors.Web.blue
                node = text {
                }
            }
        }
        node?.textColor shouldBe Colors.Web.blue
    }

    @Test
    fun textColor(){
        var node:Node? = null
        viz {
            group {
                node = text {
                    textColor = Colors.Web.green
                }
            }
        }
        node?.textColor shouldBe Colors.Web.green
    }

    @Test
    fun nodeProperty(){
        var node:Node? = null
        viz {
            fill = Colors.Web.blue
            stroke = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                stroke = Colors.Web.rebeccapurple
                strokeWidth = 3.0
                node = circle {
                    fill = Colors.Web.aqua
                    stroke = Colors.Web.aquamarine
                    strokeWidth = 5.0
                }
            }

        }
        node?.fill shouldBe Colors.Web.aqua
        node?.stroke shouldBe Colors.Web.aquamarine
        node?.strokeWidth shouldBe 5.0
    }

    @Test
    fun parentFillProperty(){
        var node:Node? = null
        viz {
            fill = Colors.Web.blue
            stroke = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                stroke = Colors.Web.rebeccapurple
                strokeWidth = 3.0
                node = circle {
                    stroke = Colors.Web.aquamarine
                    strokeWidth = 5.0
                }
            }

        }
        node?.fill shouldBe Colors.Web.blueviolet
        node?.stroke shouldBe Colors.Web.aquamarine
        node?.strokeWidth shouldBe 5.0
    }

    @Test
    fun parentStrokeProperty(){
        var node:Node? = null
        viz {
            fill = Colors.Web.blue
            stroke = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                stroke = Colors.Web.rebeccapurple
                strokeWidth = 3.0
                node = circle {
                    fill = Colors.Web.aqua
                    strokeWidth = 5.0
                }
            }

        }
        node?.fill shouldBe Colors.Web.aqua
        node?.stroke shouldBe Colors.Web.rebeccapurple
        node?.strokeWidth shouldBe 5.0
    }

    @Test
    fun parentStrokeWidthProperty(){
        var node:Node? = null
        viz {
            fill = Colors.Web.blue
            stroke = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                stroke = Colors.Web.rebeccapurple
                strokeWidth = 3.0
                node = circle {
                    fill = Colors.Web.aqua
                    stroke = Colors.Web.aquamarine
                }
            }

        }
        node?.fill shouldBe Colors.Web.aqua
        node?.stroke shouldBe Colors.Web.aquamarine
        node?.strokeWidth shouldBe 3.0
    }

    @Test
    fun vizFillProperty(){
        var node:Node? = null
        viz {
            fill = Colors.Web.blue
            stroke = Colors.Web.red
            strokeWidth = 4.0

            group {
                stroke = Colors.Web.rebeccapurple
                strokeWidth = 3.0
                node = circle {
                    stroke = Colors.Web.aquamarine
                    strokeWidth = 5.0
                }
            }

        }
        node?.fill shouldBe Colors.Web.blue
        node?.stroke shouldBe Colors.Web.aquamarine
        node?.strokeWidth shouldBe 5.0
    }

    @Test
    fun vizStrokeProperty(){
        var node:Node? = null
        viz {
            fill = Colors.Web.blue
            stroke = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                strokeWidth = 3.0
                node = circle {
                    fill = Colors.Web.aqua
                    strokeWidth = 5.0
                }
            }

        }
        node?.fill shouldBe Colors.Web.aqua
        node?.stroke shouldBe Colors.Web.red
        node?.strokeWidth shouldBe 5.0
    }

    @Test
    fun vizStrokeWidthProperty(){
        var node:Node? = null
        viz {
            fill = Colors.Web.blue
            stroke = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                stroke = Colors.Web.rebeccapurple
                node = circle {
                    fill = Colors.Web.aqua
                    stroke = Colors.Web.aquamarine
                }
            }

        }
        node?.fill shouldBe Colors.Web.aqua
        node?.stroke shouldBe Colors.Web.aquamarine
        node?.strokeWidth shouldBe 4.0
    }

}