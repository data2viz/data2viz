/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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

package io.data2viz.hierarchy.treemap

import io.data2viz.hierarchy.TreemapNode

/**
 * Like treemapSquarify, except preserves the topology (node adjacencies) of the previous layout computed by
 * treemapResquarify, if there is one and it used the same target aspect ratio.
 * This tiling method is good for animating changes to treemaps because it only changes node sizes and
 * not their relative positions, thus avoiding distracting shuffling and occlusion.
 * The downside of a stable update, however, is a suboptimal layout for subsequent updates: only the first layout
 * uses the Bruls et al. squarified algorithm.
 */
// TODO
public fun treemapResquarify(parent: TreemapNode<*>, x0:Double, y0:Double, x1:Double, y1:Double) {
    /*if ((rows = parent._squarify) && (rows.ratio === ratio)) {
        var rows,
        row,
        nodes,
        i,
        j = -1,
        n,
        m = rows.length,
        value = parent.value;

        while (++j < m) {
            row = rows[j], nodes = row.children;
            for (i = row.value = 0, n = nodes.length; i < n; ++i) row.value += nodes[i].value;
            if (row.dice) treemapDice(row, x0, y0, x1, y0 += (y1 - y0) * row.value / value);
            else treemapSlice(row, x0, y0, x0 += (x1 - x0) * row.value / value, y1);
            value -= row.value;
        }
    } else {
        parent._squarify = rows = squarifyRatio(ratio, parent, x0, y0, x1, y1);
        rows.ratio = ratio;
    }*/
}