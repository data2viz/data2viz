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

import android.graphics.RectF
import android.graphics.Bitmap
import io.data2viz.color.Color
import io.data2viz.ExperimentalD2V


public fun ImageNode.render(renderer: AndroidCanvasRenderer) {

    image?.let { img ->

        val bitmap = when (img){
            is LocalImage -> img.image
            else -> error("Unknown image type:: $img")
        }

        with(renderer) {

            val rect = when (val s = size) {
                null -> RectF(x.dp, y.dp, bitmap.width.toFloat(), bitmap.height.toFloat())
                else -> RectF(x.dp, y.dp, s.width.dp, s.height.dp)
            }

            canvas.drawBitmap(bitmap, null, rect  ,null)
        }

    }

}


public fun Bitmap.toLocalImage(): LocalImage = LocalImage(this)


public class LocalImage(public val image: Bitmap): ImageHandler

@ExperimentalD2V
//TODO: Complete BitmapImage support: https://youtrack.data2viz.io/issue/DV-158
public actual class BitmapImage actual constructor(
    private val pixels: Array<Color>,
    private val width: Int,
    private val height: Int
) : ImageHandler
