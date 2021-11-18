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

package io.data2viz.viz

import io.data2viz.geom.Rect
import io.data2viz.geom.RectGeom
import io.data2viz.viz.FontFamily
import io.data2viz.viz.FontPosture
import io.data2viz.viz.FontWeight


internal actual fun textMeasure(text: String, fontSize: Double, fontFamily: FontFamily, fontWeight: FontWeight, fontStyle: FontPosture): Rect =
    RectGeom(
        width = 10.0,
        height = 10.0
    )
