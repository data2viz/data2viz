package io.data2viz.viz

import io.data2viz.color.colors

data class Domain(val val1: Double, val val2: Double)


fun VizContext.commonViz(data: List<Domain>) {

    
    setStyle("-fx-font:20px 'sans-serif'; text-anchor:'middle'")
    
    text {
//        setStyle("-fx-font:20px 'sans-serif'; text-anchor:'middle'")
        textContent = "This a common presentation"
        y = 20.0
        x = 20.0
    }

    line {
        x1 = 111.0
        y1 = 222.0
        x2 = 211.0
        y2 = 322.0
        stroke = colors.cadetblue
    }

    rect {
        x = 30.0
        y = 130.0
        rx = 4.0
        ry = 4.0
        height = 30.0
        width = 80.0
        fill = colors.blueviolet
        stroke = colors.darkslategray
        strokeWidth = 3.0
    }
    
    group {
        
        transform { 
            translate(x = 200.0, y = 200.0)
        }
        
        path {
            fill = colors.orange
            moveTo(.0, .0)
            lineTo(100.0, 0.0)
            lineTo(.0, 100.0)
            closePath()
        }

        path {
            fill = colors.steelblue
            moveTo(.0, .0)
            lineTo(50.0, 0.0)
            lineTo(.0, 50.0)
            closePath()
        }

        path {
            fill = colors.steelblue
            moveTo(50.0, 50.0)
            lineTo(100.0, 100.0)
            lineTo(.0, 100.0)
            closePath()
        }
        
//        path {
//            arc(50.0, 50.0, 20.0, 0.0, 360.0)
//        }
    }


    group {

        group {

            data.forEach { datum ->
                circle {
                    cx = datum.val1
                    cy = datum.val2
                    radius = 5.0
                    fill = colors.steelblue
                    stroke = colors.red
                    strokeWidth = 3.0

                    transform {
                        translate(datum.val1 + 100)
                    }
                }

            }
        }
    }
}
