package io.data2viz.shape.stack

enum class StackOffsets {
    
    /**
     * Applies a zero baseline.
     */
    NONE,

    /**
     * Applies a zero baseline and normalizes the values for each point such that the topline is always one.
     */
    EXPAND,

    /**
     * Positive values are stacked above zero, while negative values are stacked below zero.
     */
    DIVERGING,

    /**
     * Shifts the baseline down such that the center of the streamgraph is always at zero.
     */
    SILHOUETTE,

    /**
     * Shifts the baseline so as to minimize the weighted wiggle of layers.
     * This offset is recommended for streamgraphs in conjunction with the inside-out order.
     * See Stacked Graphs—Geometry & Aesthetics by Bryon & Wattenberg for more information.
     * http://leebyron.com/streamgraph/
     */
    WIGGLE;

    fun <T> offset(ret: List<StackParam<T>>) {
        when (this) {
            EXPAND -> StackOffsetExpand<T>()
            DIVERGING -> StackOffsetDiverging<T>()
            SILHOUETTE -> StackOffsetSilhouette<T>()
            WIGGLE -> StackOffsetWiggle<T>()
            NONE -> StackOffsetNone<T>()
        }.offset(ret)
    }

}

interface StackOffset<T> {
    fun offset(stackParams: List<StackParam<T>>): List<StackParam<T>>
}

// TODO : cast exceptions when using irrelevant values (cf tests, ie negative values without divergingOffset...)

class StackOffsetNone<T> : StackOffset<T> {
    override fun offset(stackParams: List<StackParam<T>>): List<StackParam<T>> {
        val orderedParams = stackParams.sortedBy { it.index }
        val sums = Array<Double>(stackParams[0].stackedValues.size, { .0 })

        // JUST OFFSET EACH SERIES BY THE SUM OF ALL PRECEDING SERIES
        orderedParams.forEach { stackParam ->
            stackParam.stackedValues.forEachIndexed { index, stackSpace ->
                stackSpace.from += sums[index]
                sums[index] += stackSpace.to
                stackSpace.to = sums[index]
            }
        }
        return stackParams
    }
}

class StackOffsetExpand<T> : StackOffset<T> {
    override fun offset(stackParams: List<StackParam<T>>): List<StackParam<T>> {
        val orderedParams = stackParams.sortedBy { it.index }
        val sums = Array<Double>(stackParams[0].stackedValues.size, { .0 })

        // OFFSET EACH SERIES BY THE SUM OF ALL PRECEDING SERIES
        orderedParams.forEach { stackParam ->
            stackParam.stackedValues.forEachIndexed { index, stackSpace ->
                stackSpace.from += sums[index]
                sums[index] += stackSpace.to
                stackSpace.to = sums[index]
            }
        }

        // THEN DIVIDE BY TOTAL TO NORMALIZE ALL RESULTS SUCH AS THE TOP LINE IS ALWAYS 1
        orderedParams.forEach { stackParam ->
            stackParam.stackedValues.forEachIndexed { index, stackSpace ->
                if (sums[index] != .0) {
                    stackSpace.from /= sums[index]
                    stackSpace.to /= sums[index]
                }
            }
        }
        return stackParams
    }
}

class StackOffsetDiverging<T> : StackOffset<T> {
    override fun offset(stackParams: List<StackParam<T>>): List<StackParam<T>> {
        val orderedParams = stackParams.sortedBy { it.index }
        val sumsPositives = Array<Double>(stackParams[0].stackedValues.size, { .0 })
        val sumsNegatives = Array<Double>(stackParams[0].stackedValues.size, { .0 })

        // STACK NEGATIVE VALUES BELOW ZERO AND POSITIVE ABOVE ZERO
        orderedParams.forEach { stackParam ->
            stackParam.stackedValues.forEachIndexed { index, stackSpace ->
                if (stackSpace.to >= 0) {
                    stackSpace.from += sumsPositives[index]
                    sumsPositives[index] += stackSpace.to
                    stackSpace.to = sumsPositives[index]
                } else {
                    stackSpace.from = stackSpace.to + sumsNegatives[index]
                    val temp = stackSpace.to
                    stackSpace.to = sumsNegatives[index]
                    sumsNegatives[index] += temp

                }
            }
        }
        return stackParams
    }
}

class StackOffsetSilhouette<T> : StackOffset<T> {
    override fun offset(stackParams: List<StackParam<T>>): List<StackParam<T>> {
        val orderedParams = stackParams.sortedBy { it.index }
        val sums = Array<Double>(stackParams[0].stackedValues.size, { .0 })

        // OFFSET EACH SERIES BY THE SUM OF ALL PRECEDING SERIES
        orderedParams.forEach { stackParam ->
            stackParam.stackedValues.forEachIndexed { index, stackSpace ->
                stackSpace.from += sums[index]
                sums[index] += stackSpace.to
                stackSpace.to = sums[index]
            }
        }

        // THEN SUBSTRACT 1/2 TOTAL SUCH AS THE BASELINE IS CENTERED
        orderedParams.forEach { stackParam ->
            stackParam.stackedValues.forEachIndexed { index, stackSpace ->
                stackSpace.from -= sums[index] / 2.0
                stackSpace.to -= sums[index] / 2.0
            }
        }
        return stackParams
    }
}

class StackOffsetWiggle<T> : StackOffset<T> {
    override fun offset(stackParams: List<StackParam<T>>): List<StackParam<T>> {
        val orderedParams = stackParams.sortedBy { it.index }

        var sum = .0
        val firstSerie = orderedParams[0].stackedValues
        val seriesSize = firstSerie.size
        val dataSize = orderedParams.size

        for (serieIndex in 1 until seriesSize) {
            var s1 = .0
            var s2 = .0
            orderedParams.forEachIndexed { dataIndex, currentStackParam ->
                val sij0 = currentStackParam.stackedValues[serieIndex].to
                val sij1 = currentStackParam.stackedValues[serieIndex - 1].to
                var s3 = (sij0 - sij1) / 2
                for (k in 0 until dataIndex) {
                    val sk = orderedParams[k]
                    val skj0 = sk.stackedValues[serieIndex].to
                    val skj1 = sk.stackedValues[serieIndex - 1].to
                    s3 += skj0 - skj1
                }
                s1 += sij0
                s2 += s3 * sij0
            }
            firstSerie[serieIndex - 1].from = sum
            firstSerie[serieIndex - 1].to += firstSerie[serieIndex - 1].from
            if (s1 != .0) {
                sum -= s2 / s1
            }
        }
        firstSerie[seriesSize - 1].from = sum
        firstSerie[seriesSize - 1].to += firstSerie[seriesSize - 1].from

        return StackOffsetNone<T>().offset(stackParams)
    }
}