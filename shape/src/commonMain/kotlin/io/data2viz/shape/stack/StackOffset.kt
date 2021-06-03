/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.shape.stack

public enum class StackOffset {

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

    internal fun <T> offset(ret: List<StackParam<T>>) {
        when (this) {
            StackOffset.EXPAND -> ret.offsetExpand()
            StackOffset.DIVERGING -> ret.offsetDiverging()
            StackOffset.SILHOUETTE -> ret.offsetSilhouette()
            StackOffset.WIGGLE -> ret.offsetWiggle()
            StackOffset.NONE -> ret.offsetNone()
        }
    }

}

// TODO : cast exceptions when using irrelevant values (cf tests, ie negative values without divergingOffset...)


private fun <T> List<StackParam<T>>.offsetNone(): List<StackParam<T>> {
    val orderedParams = this.sortedBy { it.index }
    val sums = Array<Double>(this[0].stackedValues.size, { .0 })

    // JUST OFFSET EACH SERIES BY THE SUM OF ALL PRECEDING SERIES
    orderedParams.forEach { stackParam ->
        stackParam.stackedValues.forEachIndexed { index, stackSpace ->
            stackSpace.from += sums[index]
            sums[index] += stackSpace.to
            stackSpace.to = sums[index]
        }
    }
    return this
}

private fun <T> List<StackParam<T>>.offsetExpand(): List<StackParam<T>> {
    val orderedParams = this.sortedBy { it.index }
    val sums = Array<Double>(this[0].stackedValues.size, { .0 })

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
    return this
}

private fun <T> List<StackParam<T>>.offsetDiverging(): List<StackParam<T>> {
    val orderedParams = this.sortedBy { it.index }
    val sumsPositives = Array<Double>(this[0].stackedValues.size, { .0 })
    val sumsNegatives = Array<Double>(this[0].stackedValues.size, { .0 })

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
    return this
}

private fun <T> List<StackParam<T>>.offsetSilhouette(): List<StackParam<T>> {
    val orderedParams = this.sortedBy { it.index }
    val sums = Array<Double>(this[0].stackedValues.size, { .0 })

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
    return this
}

private fun <T> List<StackParam<T>>.offsetWiggle(): List<StackParam<T>> {
    val orderedParams = this.sortedBy { it.index }

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

    return this.offsetNone()
}
