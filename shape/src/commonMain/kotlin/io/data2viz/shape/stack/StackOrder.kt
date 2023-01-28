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

public enum class StackOrder {

    /**
     * Returns the given series order [0, 1, … n - 1] where n is the number of elements in series.
     * Thus, the stack order is given by the key accessor.
     */
    NONE,

    /**
     * Returns a series order such that the smallest series (according to the sum of values) is at the bottom.
     */
    ASCENDING,

    /**
     * Returns a series order such that the largest series (according to the sum of values) is at the bottom.
     */
    DESCENDING,

    /**
     * Returns the reverse of the given series order [n - 1, n - 2, … 0] where n is the number of elements in series.
     * Thus, the stack order is given by the reverse of the key accessor.
     */

    REVERSE,

    /**
     * Returns a series order such that the larger series (according to the sum of values) are on the inside and
     * the smaller series are on the outside.
     * This order is recommended for streamgraphs in conjunction with the wiggle offset.
     * See [Stacked Graphs—Geometry & Aesthetics by Byron & Wattenberg for more information.](http://leebyron.com/streamgraph/)
     */
    INSIDEOUT;

    internal fun <T> sort(stackParams: List<StackParam<T>>): List<Int> =
            when (this) {
                ASCENDING -> stackParams.sortAscending()
                DESCENDING -> stackParams.sortDescending()
                REVERSE -> stackParams.sortReverse()
                INSIDEOUT -> stackParams.sortInsideOut()
                NONE -> stackParams.sortNone()
            }
}


private fun <T> List<StackParam<T>>.sortInsideOut(): List<Int> {
    val ascendingIndexes = this.sortDescending()
    var topSum = .0
    var bottomSum = .0
    val top = mutableListOf<Int>()
    val bottom = mutableListOf<Int>()
    ascendingIndexes.forEach { index ->
        val stackParam = this[index]
        if (topSum < bottomSum) {
            top.add(stackParam.index)
            topSum += stackParam.stackedValues.sumOf { it.to }
        } else {
            bottom.add(stackParam.index)
            bottomSum += stackParam.stackedValues.sumOf { it.to }
        }
    }
    return bottom.reversed() + top
}

private fun <T> List<StackParam<T>>.sortAscending() =
        sumSeries()
                .sortedBy { it.sum }
                .map { it.index }

private fun <T> List<StackParam<T>>.sortDescending() =
        sumSeries()
                .sortedByDescending { it.sum }
                .map { it.index }

private fun <T> List<StackParam<T>>.sortNone() =
        sumSeries()
                .map { it.index }

private fun <T> List<StackParam<T>>.sortReverse(): List<Int> =
        sumSeries()
                .map { it.index }
                .reversed()

internal fun <T> List<StackParam<T>>.sumSeries(): List<SeriesSum> =
        map { serie ->
            SeriesSum(serie.index, serie.stackedValues.sumOf { it.to - it.from })
        }

internal data class SeriesSum(
        val index: Int,
        val sum: Double
)
