package io.data2viz.experiments.colorBlind

import io.data2viz.color.HSL
import io.data2viz.color.colors
import io.data2viz.core.Point
import io.data2viz.interpolate.interpolateHslLong
import io.data2viz.math.deg
import io.data2viz.svg.svg


fun colorBlind() {

    svg {
        width = 512
        height = 512

        /*var encodedColors = EncodedColors.magma.colors
        val numColors = encodedColors.size*/
        val iterator= interpolateHslLong(HSL(38.deg, 1, .5), HSL(300.deg, 1, .25))
        val encodedColors = (0 until 128).toList().map { i -> iterator(i / 128.0).toRgba() }
        val colorWidth = width.toDouble() / encodedColors.size.toDouble()
        g {
            encodedColors.forEachIndexed { index, color ->
                rect {
                    fill = color
                    transform {
                        translate(Point(index * colorWidth, 0.0))
                    }
                    width = colorWidth
                    height = 20
                }
            }
        }

        // Monochromacy
        g {
            encodedColors.forEachIndexed { index, c ->
                rect {
                    // Achromatopsia:{ R:[29.9, 58.7, 11.4], G:[29.9, 58.7, 11.4], B:[29.9, 58.7, 11.4]},
                    val rgb = colors.rgba(c.r * 0.299 + c.g * 0.587 + c.b * 0.114, c.r * 0.299 + c.g * 0.587 + c.b * 0.114, c.r * 0.299 + c.g * 0.587 + c.b * 0.114)
                    fill = rgb
                    transform {
                        translate(Point(index * colorWidth, 20.0))
                    }
                    width = colorWidth
                    height = 20
                }
            }
        }

        // Protanopia
        g {
            encodedColors.forEachIndexed { index, c ->
                rect {
                    //mat3(0.567, 0.433, 0.0  ,  0.558, 0.442, 0.0  ,  0.0  , 0.242, 0.758), // protanopia
                    val rgb = colors.rgba(c.r * 0.567 + c.g * 0.433, c.r * 0.558 + c.g * 0.442, c.g * 0.242 + c.b * 0.758)
                    fill = rgb
                    transform {
                        translate(Point(index * colorWidth, 40.0))
                    }
                    width = colorWidth
                    height = 20
                }
            }
        }

        // Deuteranopia
        g {
            encodedColors.forEachIndexed { index, c ->
                rect {
                    //Deuteranopia:{ R:[62.5, 37.5, 0], G:[70, 30, 0], B:[0, 30, 70]},
                    val rgb = colors.rgba(c.r * 0.625 + c.g * 0.375, c.r * .7 + c.g * .3, c.g * .3 + c.b * .7)
                    fill = rgb
                    transform {
                        translate(Point(index * colorWidth, 60.0))
                    }
                    width = colorWidth
                    height = 20
                }
            }
        }

        // Tritanopia
        g {
            encodedColors.forEachIndexed { index, c ->
                rect {
                    //Tritanopia:{ R:[95, 5, 0], G:[0, 43.333, 56.667], B:[0, 47.5, 52.5]},
                    val rgb = colors.rgba(c.r * .95 + c.g * .05, c.g * 0.43333 + c.b * 0.56667, c.g * 0.475 + c.b * 0.525)
                    fill = rgb
                    transform {
                        translate(Point(index * colorWidth, 80.0))
                    }
                    width = colorWidth
                    height = 20
                }
            }
        }
    }

}
