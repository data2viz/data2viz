package io.data2viz.shape

import io.data2viz.path.PathAdapter
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun <T> pie(init: PieGenerator<T>.() -> Unit) = PieGenerator<T>().apply(init)
class PieGenerator<T> {

    var value: (T) -> Double = const(.0)
    var startAngle: (T) -> Double = const(.0)
    var endAngle: (T) -> Double = const(.0)
    var padAngle: (T) -> Double = const(.0)

    /**
     * Use the data to generate a line on the context
     */
    fun <C : PathAdapter> pie(data: Array<T>, context: C): Array<ArcParams<T>> {
        val n = data.size
        var sum = .0
        val index = Array<Int>(n, { it -> 0 })
        val arcs = Array<ArcParams<T>>(n, { it -> ArcParams<T>(.0, .0, .0, null, null, null) })
        val values = Array<Double>(n, {it -> .0})
        var a0 = .0//startAngle(args)
        val da = min(tau, max(-tau, tau - a0))//min(tau, max(-tau, endAngle(args) - a0))
        val p =  min(abs(da) / n, .0)//min(abs(da) / n, padAngle(args))
        val pa = if (da < .0) -p else p

        for (i in 0 until n) {
            index[i] = i
            val v = value(data[i])
            values[i] = v
            if (v > 0) sum += v
        }

        // TODO : sorting
        // Optionally sort the arcs by previously-computed values or by data.
        //if (sortValues != null) index.sort(function(i, j) { return sortValues(arcs[i], arcs[j]); });
        //else if (sort != null) index.sort(function(i, j) { return sort(data[i], data[j]); });

        // Compute the arcs! They are stored in the original data's order.
        val k = if (sum > .0) ((da - n * pa) / sum) else .0
        for (i in 0 until n) {
            val j = index[i]
            val v = values[j]
            val a1 = a0 + (if (v > .0) (v * k) else .0) + pa
            arcs[j] = ArcParams(a0, a1, p, v,i, data[j])
            a0 = a1
        }

        return arcs
    }
}