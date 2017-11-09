package io.data2viz.shape.order

import io.data2viz.shape.StackParam

enum class StackOrders {
    NONE, ASCENDING, DESCENDING, REVERSE, INSIDEOUT
}

interface StackOrder<T> {

    fun sort(stackParams: List<StackParam<T>>): List<Int>

    /**
     * Local utility class used for storing sort-indexes and sums when sorting values
     */
    data class OrderStack(
            val index: Int,
            val sum: Double
    )

    fun sumSeries(stackParams: List<StackParam<T>>): List<OrderStack> {
        return stackParams.map { stackParam ->
            OrderStack(stackParam.index, stackParam.stackedValues.sumByDouble { it.to - it.from })
        }
    }
}

/**
 * Returns a series order such that the smallest series (according to the sum of values) is at the bottom.
 */
class StackOrderAscending<T> : StackOrder<T> {
    override fun sort(stackParams: List<StackParam<T>>): List<Int> {
        val sums = sumSeries(stackParams).sortedBy { it.sum }
        return sums.map { it.index }
    }
}

/**
 * Returns a series order such that the largest series (according to the sum of values) is at the bottom.
 */
class StackOrderDescending<T> : StackOrder<T> {
    override fun sort(stackParams: List<StackParam<T>>): List<Int> {
        val sums = sumSeries(stackParams).sortedByDescending { it.sum }
        return sums.map { it.index }
    }
}

/**
 * Returns the given series order [0, 1, … n - 1] where n is the number of elements in series.
 * Thus, the stack order is given by the key accessor.
 */
class StackOrderNone<T> : StackOrder<T> {
    override fun sort(stackParams: List<StackParam<T>>): List<Int> {
        return stackParams.map { it.index }
    }
}

/**
 * Returns the reverse of the given series order [n - 1, n - 2, … 0] where n is the number of elements in series.
 * Thus, the stack order is given by the reverse of the key accessor.
 */
class StackOrderReverse<T> : StackOrder<T> {
    override fun sort(stackParams: List<StackParam<T>>): List<Int> {
        return stackParams.map { it.index }.reversed()
    }
}

/**
 * Returns a series order such that the larger series (according to the sum of values) are on the inside and
 * the smaller series are on the outside.
 * This order is recommended for streamgraphs in conjunction with the wiggle offset.
 * See Stacked Graphs—Geometry & Aesthetics by Byron & Wattenberg for more information.
 * http://leebyron.com/streamgraph/
 */
class StackOrderInsideOut<T> : StackOrder<T> {
    override fun sort(stackParams: List<StackParam<T>>): List<Int> {
        val ascendingIndexes = StackOrderDescending<T>().sort(stackParams)
        var topSum = .0
        var bottomSum = .0
        val top = mutableListOf<Int>()
        val bottom = mutableListOf<Int>()
        ascendingIndexes.forEach { index ->
            val stackParam = stackParams.get(index)
            if (topSum < bottomSum) {
                top.add(stackParam.index)
                topSum += stackParam.stackedValues.sumByDouble { it.to }
            } else {
                bottom.add(stackParam.index)
                bottomSum += stackParam.stackedValues.sumByDouble { it.to }
            }
        }
        return bottom.reversed() + top
    }
}