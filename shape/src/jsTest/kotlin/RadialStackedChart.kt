import io.data2viz.format.formatter
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import io.data2viz.shape.ArcBuilder
import io.data2viz.shape.arcBuilder
import io.data2viz.shape.pi
import io.data2viz.shape.tau
import org.w3c.dom.Text
import org.w3c.dom.svg.SVGElement
import kotlin.browser.document
import kotlin.math.sqrt

data class Population(
        val state: String,
        val reparition: Array<Int>
)

val format = formatter(".2s")

val colors = arrayOf<String>("#eff3ff", "#c6dbef", "#9ecae1", "#6baed6", "#3182bd", "#08519c", "#084594", "#08306b")
val thresholds = arrayOf<Int>(5000000, 10000000, 15000000, 20000000, 25000000, 30000000, 35000000)

//State,Under 5 Years,5 to 13 Years,14 to 17 Years,18 to 24 Years,25 to 44 Years,45 to 64 Years,65 Years and Over
val data = weave(arrayOf(
        Population("AL", arrayOf(310504, 552339, 259034, 450818, 1231572, 1215966, 641667)),
        Population("AK", arrayOf(52083, 85640, 42153, 74257, 198724, 183159, 50277)),
        Population("AZ", arrayOf(515910, 828669, 362642, 601943, 1804762, 1523681, 862573)),
        Population("AR", arrayOf(202070, 343207, 157204, 264160, 754420, 727124, 407205)),
        Population("CA", arrayOf(2704659, 4499890, 2159981, 3853788, 10604510, 8819342, 4114496)),
        Population("CO", arrayOf(358280, 587154, 261701, 466194, 1464939, 1290094, 511094)),
        Population("CT", arrayOf(211637, 403658, 196918, 325110, 916955, 968967, 478007)),
        Population("DE", arrayOf(59319, 99496, 47414, 84464, 230183, 230528, 121688)),
        Population("DC", arrayOf(36352, 50439, 25225, 75569, 193557, 140043, 70648)),
        Population("FL", arrayOf(1140516, 1938695, 925060, 1607297, 4782119, 4746856, 3187797)),
        Population("GA", arrayOf(740521, 1250460, 557860, 919876, 2846985, 2389018, 981024)),
        Population("HI", arrayOf(87207, 134025, 64011, 124834, 356237, 331817, 190067)),
        Population("ID", arrayOf(121746, 201192, 89702, 147606, 406247, 375173, 182150)),
        Population("IL", arrayOf(894368, 1558919, 725973, 1311479, 3596343, 3239173, 1575308)),
        Population("IN", arrayOf(443089, 780199, 361393, 605863, 1724528, 1647881, 813839)),
        Population("IA", arrayOf(201321, 345409, 165883, 306398, 750505, 788485, 444554)),
        Population("KS", arrayOf(202529, 342134, 155822, 293114, 728166, 713663, 366706)),
        Population("KY", arrayOf(284601, 493536, 229927, 381394, 1179637, 1134283, 565867)),
        Population("LA", arrayOf(310716, 542341, 254916, 471275, 1162463, 1128771, 540314)),
        Population("ME", arrayOf(71459, 133656, 69752, 112682, 331809, 397911, 199187)),
        Population("MD", arrayOf(371787, 651923, 316873, 543470, 1556225, 1513754, 679565)),
        Population("MA", arrayOf(383568, 701752, 341713, 665879, 1782449, 1751508, 871098)),
        Population("MI", arrayOf(625526, 1179503, 585169, 974480, 2628322, 2706100, 1304322)),
        Population("MN", arrayOf(358471, 606802, 289371, 507289, 1416063, 1391878, 650519)),
        Population("MS", arrayOf(220813, 371502, 174405, 305964, 764203, 730133, 371598)),
        Population("MO", arrayOf(399450, 690476, 331543, 560463, 1569626, 1554812, 805235)),
        Population("MT", arrayOf(61114, 106088, 53156, 95232, 236297, 278241, 137312)),
        Population("NE", arrayOf(132092, 215265, 99638, 186657, 457177, 451756, 240847)),
        Population("NV", arrayOf(199175, 325650, 142976, 212379, 769913, 653357, 296717)),
        Population("NH", arrayOf(75297, 144235, 73826, 119114, 345109, 388250, 169978)),
        Population("NJ", arrayOf(557421, 1011656, 478505, 769321, 2379649, 2335168, 1150941)),
        Population("NM", arrayOf(148323, 241326, 112801, 203097, 517154, 501604, 260051)),
        Population("NY", arrayOf(1208495, 2141490, 1058031, 1999120, 5355235, 5120254, 2607672)),
        Population("NC", arrayOf(652823, 1097890, 492964, 883397, 2575603, 2380685, 1139052)),
        Population("ND", arrayOf(41896, 67358, 33794, 82629, 154913, 166615, 94276)),
        Population("OH", arrayOf(743750, 1340492, 646135, 1081734, 3019147, 3083815, 1570837)),
        Population("OK", arrayOf(266547, 438926, 200562, 369916, 957085, 918688, 490637)),
        Population("OR", arrayOf(243483, 424167, 199925, 338162, 1044056, 1036269, 503998)),
        Population("PA", arrayOf(737462, 1345341, 679201, 1203944, 3157759, 3414001, 1910571)),
        Population("RI", arrayOf(60934, 111408, 56198, 114502, 277779, 282321, 147646)),
        Population("SC", arrayOf(303024, 517803, 245400, 438147, 1193112, 1186019, 596295)),
        Population("SD", arrayOf(58566, 94438, 45305, 82869, 196738, 210178, 116100)),
        Population("TN", arrayOf(416334, 725948, 336312, 550612, 1719433, 1646623, 819626)),
        Population("TX", arrayOf(2027307, 3277946, 1420518, 2454721, 7017731, 5656528, 2472223)),
        Population("UT", arrayOf(268916, 413034, 167685, 329585, 772024, 538978, 246202)),
        Population("VT", arrayOf(32635, 62538, 33757, 61679, 155419, 188593, 86649)),
        Population("VA", arrayOf(522672, 887525, 413004, 768475, 2203286, 2033550, 940577)),
        Population("WA", arrayOf(433119, 750274, 357782, 610378, 1850983, 1762811, 783877)),
        Population("WV", arrayOf(105435, 189649, 91074, 157989, 470749, 514505, 285067)),
        Population("WI", arrayOf(362277, 640286, 311849, 553914, 1487457, 1522038, 750146)),
        Population("WY", arrayOf(38253, 60890, 29314, 53980, 137338, 147279, 65614))
))

@JsName("stackedChart")
fun stackedChart() {
    val generator = arcBuilder<Population> {
        cornerRadius = { .0 }
    }
    renderPieSvg(generator, "d2vSamples")
}

private fun renderPieSvg(arcBuilder: ArcBuilder<Population>, elementId: String) {
    val states = data.size
    val factorRatio = 140.0
    val innerMargin = 150.0
    val textOffset = 525
    with(document.getElementById(elementId)!!) {
        val labelGroup = createSvgElement("g").apply {
            setAttribute("transform", "translate(400,670)")
        }
        appendChild(createSvgElement("svg").apply {
            setAttribute("height", "800")
            setAttribute("width", "900")
            setAttribute("viewBox", "0 0 800 900")
            setAttribute("style", "height:85vh")
            data.forEachIndexed { i, population ->
                var sum = .0
                population.reparition.forEachIndexed { index, value ->
                    appendChild(createSvgElement("path").apply {
                        val inner = innerMargin + sqrt(sum)
                        arcBuilder.innerRadius = { inner }
                        val outer = innerMargin + sqrt(sum + (value.toDouble() / factorRatio))
                        // arcBuilder.outerRadius = { pop -> pop.reparition.average() }
                        arcBuilder.outerRadius = { outer }
                        arcBuilder.startAngle = { (i.toDouble() / states) * tau }
                        arcBuilder.endAngle = { ((i.toDouble() + 1.0) / states) * tau }
                        val line = arcBuilder.buildArcForDatum(Population("", emptyArray()), PathGeom()).svgPath
                        setAttribute("d", line)
                        setAttribute("transform", "translate(400,670)")
                        setAttribute("stroke", colors[index + 1])
                        setAttribute("fill", colors[index])
                        sum += value.toDouble() / factorRatio
                    })
                }
                val textRotation = ((i * (180.0 / 53.0) * -180) / (pi - 90)) - 86
                labelGroup.appendChild(createSvgElement("g").apply {
                    setAttribute("transform", "rotate($textRotation)translate(139, 0)")
                }).appendChild(createSvgElement("text").apply {
                    setAttribute("text-anchor", "middle")
                    setAttribute("fill", "#000")
                    setAttribute("stroke", "#444")
                    setAttribute("font-size", "10")
                    setAttribute("transform", if (textRotation < 0 || textRotation > 180) "rotate(90)" else "rotate(-90)translate(0,8)")
                }).apply {
                    appendChild(createTextNode(population.state))
                }
            }
            appendChild(labelGroup)
            appendChild(createSvgElement("g").apply {
                setAttribute("fill", "none")
                setAttribute("stroke", "#444")
                thresholds.forEach { threshold ->
                    appendChild(createSvgElement("circle").apply {
                        setAttribute("cx", "400")
                        setAttribute("cy", "670")
                        setAttribute("r", "${innerMargin + sqrt(threshold.toDouble() / factorRatio)}")
                    })
                    appendChild(createSvgElement("text").apply {
                        setAttribute("text-anchor", "middle")
                        setAttribute("x", "400")
                        setAttribute("y", "${textOffset - sqrt(threshold / factorRatio)}")
                        setAttribute("fill", "none")
                        setAttribute("font-size", "12")
                        setAttribute("stroke", "#fff")
                        setAttribute("stroke-width", "4")
                        setAttribute("stroke-linejoin", "round")
                    }).apply {
                        appendChild(createTextNode(format(threshold.toDouble())))
                    }
                    appendChild(createSvgElement("text").apply {
                        setAttribute("text-anchor", "middle")
                        setAttribute("x", "400")
                        setAttribute("y", "${textOffset - sqrt(threshold.toDouble() / factorRatio)}")
                        setAttribute("fill", "#444")
                        setAttribute("font-size", "12")
                        setAttribute("stroke", "#444")
                        setAttribute("stroke-width", "1")
                    }).apply {
                        appendChild(createTextNode(format(threshold.toDouble())))
                    }
                }
            })
        })
    }
}

fun createSvgElement(name: String): SVGElement  = document.createElementNS("http://www.w3.org/2000/svg", name) as SVGElement

fun createTextNode(content: String): Text {
    return document.createTextNode(content)
}

fun weave(array: Array<Population>): Array<Population> {
    var i = 0
    val n = array.size
    val sorted = array.sortedWith(compareByDescending({ it.reparition[5] }))
    val weave = emptyArray<Population>()

    while (i < n) {
        val j = 2 * i
        weave[i] = sorted[if (j >= n) ((n - i) * 2 - 1) else j]
        i++
    }
    return weave
}
