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

import javafx.scene.canvas.*
import javafx.scene.image.Image

public fun ImageNode.render(gc: GraphicsContext) {

    image?.let { img ->
        val canvasImageSource = when (img){
            is LocalImage -> img.image
            else -> error("Unknown image type:: $img")
        }

        size?.let { s ->
            gc.drawImage(canvasImageSource, x, y, s.width, s.height)
        } ?: {
            gc.drawImage(canvasImageSource, x, y)
        }

    }

}


public fun Image.toLocalImage(): LocalImage = LocalImage(this)


public class LocalImage(
    public val image: Image): ImageHandler

