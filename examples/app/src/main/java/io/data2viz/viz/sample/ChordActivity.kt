package io.data2viz.viz.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.content.Context
import android.graphics.Canvas
import android.view.View
import io.data2viz.color.colors
import io.data2viz.examples.chord.chordViz
import io.data2viz.viz.AndroidCanvasRenderer
import io.data2viz.viz.Viz


class ChordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ChordView(this))
    }

}


val size = 10.0

val translateViz = Viz().apply {

    with(root){

        rect {
            width = size
            height = size
            fill = colors.black
        }

        group {
            transform { translate(size, size) }
            rect {
                width = size
                height = size
                fill = colors.black
            }
            group {
                transform { translate(size, size) }
                rect {
                    width = size
                    height = size
                    fill = colors.black
                }
                group {
                    transform { translate(size, size) }
                    rect {
                        width = size
                        height = size
                        fill = colors.black
                    }

                    group {
                        transform { translate(size, size) }
                        rect {
                            width = size
                            height = size
                            fill = colors.black
                        }

                    }
                    rect {
                        y = size * 2
                        width = size
                        height = size
                        fill = colors.black
                    }
                }
                rect {
                    y = size * 4
                    width = size
                    height = size
                    fill = colors.black
                }
            }
            rect {
                y = size * 6
                width = size
                height = size
                fill = colors.black
            }
        }
        rect {
            y = size * 8
            width = size
            height = size
            fill = colors.black
        }

    }
}


class ChordView(context: Context) : View(context) {

    val renderer = AndroidCanvasRenderer(context, Canvas()).apply {
        scale = 1.8F
    }


    override fun onDraw(canvas: Canvas) {
        println("Draw")
        renderer.canvas = canvas
        renderer.render(chordViz())
    }

}


