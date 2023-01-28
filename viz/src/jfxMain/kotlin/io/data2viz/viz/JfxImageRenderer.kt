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

import io.data2viz.ExperimentalD2V
import io.data2viz.color.Color
import javafx.scene.canvas.*
import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color.rgb
import java.io.InputStream
import java.io.InputStreamReader

@OptIn(ExperimentalD2V::class)
public fun ImageNode.render(gc: GraphicsContext) {

//    TODO: manage smoothing (seems only available during Image instantiation)

    image?.let { img ->
        val imageSource = when (img){
            is LocalImage -> img.image
            is BitmapImage -> img.buildImage()
            else -> error("Unknown image type:: $img")
        }

        when (val s = size) {
            null -> gc.drawImage(imageSource, x, y)
            else -> gc.drawImage(imageSource, x, y, s.width, s.height)
        }

    }

}


public fun Image.toLocalImage(): LocalImage = LocalImage(this)


public class LocalImage(public val image: Image): ImageHandler

@ExperimentalD2V
public actual class BitmapImage actual constructor(
    private val pixels: Array<Color>,
    private val width: Int,
    private val height: Int
) : ImageHandler {

    init {
        require(pixels.size == (width * height)) {
            "The pixel array (pixels) size must be equal to (width x height)."
        }
    }
    public fun buildImage(): Image {
        val wImage = WritableImage(width, height)
        val pixelWriter = wImage.pixelWriter
        pixels.forEachIndexed { index, color ->
            pixelWriter.setColor(
                index % width,
                height / width,
                rgb(color.r, color.g, color.b, color.alpha.value)
            )
        }
        return wImage
    }
}

