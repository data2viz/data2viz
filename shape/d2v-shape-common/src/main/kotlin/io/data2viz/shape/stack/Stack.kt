package io.data2viz.shape.stack

import io.data2viz.shape.const

data class StackSpace<T>(
        var from: Double,
        var to: Double,
        val paramIndex: Int,
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
    var order: StackOrder = StackOrder.NONE
    var offset: StackOffset = StackOffset.NONE

    fun stack(data: Array<T>): Array<StackParam<T>> {
        val ret = mutableListOf<StackParam<T>>()

        // BUILDING : build the StackParam and StackSpace that function will return
        val firstValue = series(data[0])
        firstValue.forEachIndexed { index, _ ->
            val stackedValues = mutableListOf<StackSpace<T>>()
            val stack = StackParam(stackedValues, index)
            ret.add(stack)
        }
        data.forEachIndexed { index1, element ->
            series(element).forEachIndexed { index2, serie ->
                val stack = ret[index2]
                stack.stackedValues.add(StackSpace(.0, serie, index1, data[index1]))
            }
        }

        // ORDERING : order series depending on its sum
        val indexes = order.sort(ret)

        indexes.forEachIndexed { realIndex, oldIndex ->
            ret[oldIndex].index = realIndex
        }

        // OFFSETTING : place values along the baseline and normalize them if needed
        offset.offset(ret)

        return ret.toTypedArray()
    }
}