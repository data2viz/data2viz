package io.data2viz.experiments.walking

import io.data2viz.color.colors
import io.data2viz.interpolate.linkedTo
import io.data2viz.interpolate.scale
import io.data2viz.math.deg
import io.data2viz.request.*
import io.data2viz.svg.Margins
import io.data2viz.svg.svg


external class Date {
    companion object {
        fun parse(stringDate: String): Date
        fun UTC(year: Int,
                month: Int,
                day: Int = definedExternally,
                hour: Int = definedExternally,
                minute: Int = definedExternally,
                second: Int = definedExternally,
                millisecond: Int = definedExternally): Date
    }

    fun getTime(): Double
}

data class Episode(
        val Season: Int,
        val Episode: Int,
        val SeriesNumber: Int,
        val Title: String,
        val USViewers: Long,
        val FirstAirDate: Date)


fun walkingDead() {
    request("https://gist.githubusercontent.com/d3byex/e5ce6526ba2208014379/raw/8fefb14cc18f0440dc00248f23cbf6aec80dcc13/walking_dead_s5.json")
//    request("https://gist.githubusercontent.com/d3byex/e5ce6526ba2208014379/raw/8fefb14cc18f0440dc00248f23cbf6aec80dcc13/walking_dead_s5.csv")
            .get { xhr ->

                val episodes: Array<Episode> = JSON.parse(xhr.responseText)

//            val episodes = Dsv().parseRows(xhr.responseText)
//                    .drop(1)
//                    .map {
//                        Episode(it[0].toInt(), it[1].toInt(), it[2].toInt(), it[3], it[5].trim().toLong(), Date.parse(it[4])
//                        )
//                    }
                val maxViewers = episodes.maxBy { it.USViewers }!!.USViewers
                val margin = Margins(10, 10, 260, 85)
                val graphWidth = 500
                val graphHeight = 300

                val totalWidth = graphWidth + margin.horizontalMargins
                val totalHeight = graphHeight + margin.verticalMargins

                val yScale = io.data2viz.interpolate.scale.linear.numberToNumber(
                        0 linkedTo 0,
                        maxViewers linkedTo graphHeight
                )

                val axisPadding = 3

                fun Episode.key() = "$Season-$Episode"

                val bands = scale.ordinal.bands(episodes.map { it.key() }) {
                    stop = graphWidth
                    padding = padding(0.05)
                }

                svg {
                    width = totalWidth
                    height = totalHeight

                    val mainGroup = g {
                        transform {
                            translate(margin.left, margin.top)
                        }

                        episodes.forEachIndexed { index, episode ->
                            g {
                                transform {
                                    translate(bands(episode.key()), graphHeight - yScale(episode.USViewers).toDouble())
                                }

                                rect {
                                    fill = colors.steelblue
                                    width = bands.bandwidth
                                    height = yScale(episode.USViewers)
                                }

                                text {
                                    text = episode.USViewers.toString()
                                    style {
                                        setAttribute("text-anchor", "start")
                                    }
                                    setAttribute("dx", "10")
                                    setAttribute("dy", "-10")
                                    fill = colors.white
                                    transform {
                                        rotate(90.deg)
                                    }
                                }
                            }
                        }
                    }
                }
            }
}
