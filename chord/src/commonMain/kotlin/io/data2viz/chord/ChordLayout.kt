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

package io.data2viz.chord

import kotlin.math.PI
import kotlin.math.max

/**class ChordMatrix<out D>(val size: Int, private val list: List<D>) {

    private val listSize = size * size
    operator fun get(x: Int, y: Int): D = list[y * size + x]

    init {
        require(list.size == listSize, { "List size (${list.size}) is invalid, should be $size x $size = $listSize." })
    }
}**/

// TODO : use Angle
public data class ChordSubgroup(
        val index: Int = 0,
        val subIndex: Int = 0,
        val startAngle: Double = .0,
        val endAngle: Double = .0,
        val value: Double = .0
)

// TODO : use Angle
public data class ChordGroup(
    val index: Int = 0,
    val startAngle: Double = .0,
    val endAngle: Double = .0,
    val value: Double = .0
)

public data class Chord(
    val source:ChordSubgroup,
    val target:ChordSubgroup
)

public data class Chords(
    val chords: List<Chord>,
    val groups: List<ChordGroup>
)

public val emptyGroup: ChordGroup = ChordGroup()
public val emptySubgroup: ChordSubgroup = ChordSubgroup()

// TODO add kind of "magic-sort" to reduce overlap see https://gist.github.com/nbremer/864b11eb83aac3a1f6a2#file-d3-layout-chord-sort-js

/**
 * Computes the chord layout for the specified data and flow-accessor.
 * The "data" represents the list of nodes in the Chord Diagram.
 * The "flow" accessor give the flow value from each node to each node (including itself), value must be >= 0.
 *
 * The return value of chord() is an a Chords object containing:
 * 1 - chords: an array of Chord, each chord represents the combined bidirectional flow between 2 nodes A and B:
 *      - source:  the source ChordSubgroup
 *      - target:  the target ChordSubgroup
 *
 *      Each source and target ChordSubgroup is also an object with the following properties:
 *          - startAngle: the start angle in radians
 *          - endAngle:  the end angle in radians
 *          - value: the flow value (Double >= 0)
 *          - index - the node index A
 *          - subIndex - the node index B
 *
 * The chords are typically passed to a ChordRibbon to display the network relationships.
 * The returned array includes only chord objects for which the value flow(A, B) or flow(B, A) is non-zero.
 * Furthermore, the returned array only contains unique chords: a given chord AB represents the bidirectional
 * flow from A to B and from B to A, and does not contain a duplicate chord BA;
 * A and B are chosen such that the chord’s source always represents the larger of flow(A, B) and flow(B, A).
 * In other words, chord.source.index equals chord.target.subIndex, chord.source.subIndex equals chord.target.index,
 * chord.source.value is greater than or equal to chord.target.value, and chord.source.value is always > 0.
 *
 * 2- groups : an array of size = data.size, where each group represents the combined outflow for node A,
 * corresponding to all the links between A and all nodes, and is an object with the following properties:
 *      - startAngle:  the start angle in radians
 *      - endAngle: the end angle in radians
 *      - value: the total outgoing flow value for node A
 *      - index: the node index A
 *
 * The groups are typically passed to an Arc to produce a donut chart around the circumference of the chord layout.
 */
public class ChordLayout<D> {

    /**
     * Sets the pad angle between adjacent groups to the specified number in radians.
     */
    public var padAngle: Double = .0

    /**
     * Sets the group comparator to the specified function or null.
     * If the group comparator is non-null, it is used to sort the groups.
     */
    // TODO : change to Comparator<ChordGroup>? (note that that also need to alorithm sorting order on main method
    public var sortGroups:Comparator<Double>? = null

    /**
     * Sets the subgroup comparator to the specified function or null.
     * If the subgroup comparator is non-null, it is used to sort the subgroups for a given group.
     */
    public var sortSubgroups:Comparator<Double>? = null

    /**
     * Sets the chord comparator to the specified function or null.
     * If the chord comparator is non-null, it is used to sort the chords; this only affects the z-order of the chords.
     */
    public var sortChords:Comparator<Double>? = null

    public fun chord(data: List<D>, flow: (from: D, to: D) -> Double): Chords {

        val n = data.size
        val sizeRange = 0 until n

        val groupSums = List(n, { .0 }).toTypedArray()
        val groups = List(n, { emptyGroup }).toTypedArray()
        val subgroups = List(n * n, { emptySubgroup }).toTypedArray()

        // Lists of groups and subgroups indexes (used when sorting groups & subgroups)
        val groupIndex = sizeRange.toList().toTypedArray()
        val subgroupIndex = sizeRange.map { sizeRange.toList().toTypedArray() }

        val chords = mutableListOf<Chord>()

        // Compute the sum.
        var x: Double
        var k = .0
        data.forEachIndexed { indexa, a ->
            x = .0
            data.forEach { b ->
                x += flow(a, b)
            }
            groupSums[indexa] = x
            k += x
        }

        // Sort groups…
        if (sortGroups != null) groupIndex.sortWith(Comparator { a, b ->
            sortGroups!!.compare(groupSums[a], groupSums[b])
        })

        // Sort subgroups…
        if (sortSubgroups != null) subgroupIndex.forEachIndexed { index, subGroup ->
            subGroup.sortWith(Comparator { a, b ->
                sortSubgroups!!.compare(flow(data[index], data[a]), flow(data[index], data[b]))
            })
        }

        // Convert the sum to scaling factor for [0, 2pi].
        // TODO Allow start and end angle to be specified?
        // TODO Allow padding to be specified as percentage?
        k = max(.0, (2 * PI) - padAngle * n) / k
        val dx = if (k != .0) padAngle else (2 * PI / n)

        // Compute the start and end angle for each group and subgroup.
        // Note: Opera has a bug reordering object literal properties!
        x = .0
        groupIndex.forEach { di ->
            val x0 = x
            subgroupIndex[di].forEach { dj ->
                val v = flow(data[di], data[dj])
                val a0 = x
                x += v * k
                val a1 = x
                subgroups[dj * n + di] = ChordSubgroup(di, dj, a0, a1, v)
            }
            groups[di] = ChordGroup(di, x0, x, groupSums[di])
            x += dx
        }

        // Generate chords for each (non-empty) subgroup-subgroup link.
        sizeRange.forEach { i ->
            (i until n).forEach { j ->
                val source = subgroups[j * n + i]
                val target = subgroups[i * n + j]
                if (source.value != .0 || target.value != .0) {
                    if (source.value < target.value) chords.add(Chord(target, source))
                    else chords.add(Chord(source, target))
                }
            }
        }

        // TODO SORT
        val ret = Chords(chords, groups.toList())
        /*if (sortChords != null) {
            chords.sortWith(Comparator { a, b ->
                sortChords!!.compare(groupSums[a], groupSums[b])
            })
        }*/
        return ret
    }

}
