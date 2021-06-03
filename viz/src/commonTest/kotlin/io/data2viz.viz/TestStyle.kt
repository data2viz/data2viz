/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

package io.data2viz.viz

import io.data2viz.color.Colors
import io.data2viz.test.TestBase
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
    fun nodeProperty() {
        var node: Node? = null
        viz {
            fill = Colors.Web.blue
            strokeColor = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                strokeColor = Colors.Web.rebeccapurple
                strokeWidth = 3.0
                node = circle {
                    fill = Colors.Web.aqua
                    strokeColor = Colors.Web.aquamarine
                    strokeWidth = 5.0
                }
            }

        }
        node?.fill shouldBe Colors.Web.aqua
        node?.strokeColor shouldBe Colors.Web.aquamarine
        node?.strokeWidth shouldBe 5.0
    }

    @Test
    fun parentFillProperty() {
        var node: Node? = null
        viz {
            fill = Colors.Web.blue
            strokeColor = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                strokeColor = Colors.Web.rebeccapurple
                strokeWidth = 3.0
                node = circle {
                    strokeColor = Colors.Web.aquamarine
                    strokeWidth = 5.0
                }
            }

        }
        node?.fill shouldBe Colors.Web.blueviolet
        node?.strokeColor shouldBe Colors.Web.aquamarine
        node?.strokeWidth shouldBe 5.0
    }

    @Test
    fun parentStrokeProperty() {
        var node: Node? = null
        viz {
            fill = Colors.Web.blue
            strokeColor = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                strokeColor = Colors.Web.rebeccapurple
                strokeWidth = 3.0
                node = circle {
                    fill = Colors.Web.aqua
                    strokeWidth = 5.0
                }
            }

        }
        node?.fill shouldBe Colors.Web.aqua
        node?.strokeColor shouldBe Colors.Web.rebeccapurple
        node?.strokeWidth shouldBe 5.0
    }

    @Test
    fun parentStrokeWidthProperty() {
        var node: Node? = null
        viz {
            fill = Colors.Web.blue
            strokeColor = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                strokeColor = Colors.Web.rebeccapurple
                strokeWidth = 3.0
                node = circle {
                    fill = Colors.Web.aqua
                    strokeColor = Colors.Web.aquamarine
                }
            }

        }
        node?.fill shouldBe Colors.Web.aqua
        node?.strokeColor shouldBe Colors.Web.aquamarine
        node?.strokeWidth shouldBe 3.0
    }

    @Test
    fun vizFillProperty() {
        var node: Node? = null
        viz {
            fill = Colors.Web.blue
            strokeColor = Colors.Web.red
            strokeWidth = 4.0

            group {
                strokeColor = Colors.Web.rebeccapurple
                strokeWidth = 3.0
                node = circle {
                    strokeColor = Colors.Web.aquamarine
                    strokeWidth = 5.0
                }
            }

        }
        node?.fill shouldBe Colors.Web.blue
        node?.strokeColor shouldBe Colors.Web.aquamarine
        node?.strokeWidth shouldBe 5.0
    }

    @Test
    fun vizStrokeProperty() {
        var node: Node? = null
        viz {
            fill = Colors.Web.blue
            strokeColor = Colors.Web.red
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
        node?.strokeColor shouldBe Colors.Web.red
        node?.strokeWidth shouldBe 5.0
    }

    @Test
    fun vizStrokeWidthProperty() {
        var node: Node? = null
        viz {
            fill = Colors.Web.blue
            strokeColor = Colors.Web.red
            strokeWidth = 4.0

            group {
                fill = Colors.Web.blueviolet
                strokeColor = Colors.Web.rebeccapurple
                node = circle {
                    fill = Colors.Web.aqua
                    strokeColor = Colors.Web.aquamarine
                }
            }

        }
        node?.fill shouldBe Colors.Web.aqua
        node?.strokeColor shouldBe Colors.Web.aquamarine
        node?.strokeWidth shouldBe 4.0
    }

}
