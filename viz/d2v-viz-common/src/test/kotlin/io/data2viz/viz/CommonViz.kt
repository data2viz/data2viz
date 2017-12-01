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
            translate(x = 77.7, y = 250.0)
        }
        
        path {
            fill = null
            stroke = colors.aquamarine
            strokeWidth = 3.0
            
            moveTo(10.0, 10.0)
            lineTo(70.0, 10.0)
            quadraticCurveTo(85.0, 60.0, 100.0, 20.0)
            lineTo(175.0, 55.0)
            lineTo(10.0, 100.0)
            closePath()

//            moveTo(50.0, 50.0)
            arc(50.0, 50.0, 20.0, 0.0, 360.0)        }
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
