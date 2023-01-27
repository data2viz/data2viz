/*
 * Copyright (c) 2018-2020. data2viz sàrl.
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
import io.data2viz.geom.Size


/**
 * Links an image that needs to be rendered in the viz.
 */
public class ImageNode : Node() {

    public var image: ImageHandler? = null

    /**
     * x position of the top left point
     */
    public var x: Double = .0

    /**
     * y position of the top left point
     */
    public var y: Double = .0

    /**
     * The target size of the image
     */
    public var size: Size? = null

    /**
     * Image smoothing when scaled.
     * Warning: currently unsupported on JFX.
     */
    @ExperimentalD2V
    public var smoothing: Boolean = true
}

/**
 * Represents an image in the specific platform. The loading
 * of the image is outside the common code.
 *
 * Depending on the platform, it can rely on Image Element (like HTMLImageElement) or
 * on class loading mechanisms.
 */
public interface ImageHandler

/**
 * Instantiate an [ImageHandler] from an array of (width x height) [Color]s, useful for generating heatmaps.
 * Then the generated [ImageNode] can be stretched with [ImageNode.size] allowing to use the GPU
 * and [ImageNode.smoothing] to handle high performance colors interpolation.
 *
 * @param pixels: the array of [Color], one for each pixel drawn, its size must be (width x height)
 * @param width: the width of the array (unrelated to [ImageNode].size)
 * @param height: the height of the array (unrelated to [ImageNode].size)
 */
@ExperimentalD2V
public expect class BitmapImage(pixels: Array<Color>, width: Int, height: Int): ImageHandler
