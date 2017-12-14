package io.data2viz.shape

import io.data2viz.shape.offset.*
import io.data2viz.shape.order.*

data class StackSpace<T>(
        var from: Double,
        var to: Double,
        val data: T
)

data class StackParam<T>(
        val stackedValues: MutableList<StackSpace<T>>,
//        val data: T,
        var index: Int
)

// TODO : use "serieIndex" and "dataIndex" to help understand the algorithm

fun <T> stack(init: StackGenerator<T>.() -> Unit) = StackGenerator<T>().apply(init)
class StackGenerator<T> {

    var series: (T) -> Array<Double> = const(arrayOf(.0))
    var order: StackOrders = StackOrders.NONE
    var offset: StackOffsets = StackOffsets.NONE

    fun stack(data: Array<T>): Array<StackParam<T>> {
        val ret = mutableListOf<StackParam<T>>()

        // BUILDING : build the StackParam and StackSpace that function will return
        val firstValue = series(data[0])
        firstValue.forEachIndexed { index, _ ->
            val stackedValues = mutableListOf<StackSpace<T>>()
            val stack = StackParam<T>(stackedValues, index)
            ret.add(stack)
        }
        data.forEachIndexed { index1, element ->
            series(element).forEachIndexed { index2, serie ->
                val stack = ret[index2]
                stack.stackedValues.add(StackSpace(.0, serie, data[index1]))
            }
        }

        // ORDERING : order series depending on its sum
        if (order != StackOrders.NONE) {
            val indexes = when (order) {
                StackOrders.ASCENDING -> StackOrderAscending<T>().sort(ret)
                StackOrders.DESCENDING -> StackOrderDescending<T>().sort(ret)
                StackOrders.REVERSE -> StackOrderReverse<T>().sort(ret)
                StackOrders.INSIDEOUT -> StackOrderInsideOut<T>().sort(ret)
                else -> StackOrderNone<T>().sort(ret)
            }

            indexes.forEachIndexed { realIndex, oldIndex ->
                ret[oldIndex].index = realIndex
            }
        }

        // OFFSETING : place values along the baseline and normalize them if needed
        when (offset) {
            StackOffsets.EXPAND -> StackOffsetExpand<T>().offset(ret)
            StackOffsets.DIVERGING -> StackOffsetDiverging<T>().offset(ret)
            StackOffsets.SILHOUETTE -> StackOffsetSilhouette<T>().offset(ret)
            StackOffsets.WIGGLE -> StackOffsetWiggle<T>().offset(ret)
            else -> StackOffsetNone<T>().offset(ret)
        }

        return ret.toTypedArray()
    }
}