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

import io.data2viz.geom.Size


/**
 * Links an image that needs to be rendered in the viz.
 */
class ImageNode() : Node() {
    var image: ImageHandler? = null

    /**
     * x position of the top left point
     */
    var x: Double = .0

    /**
     * y position of the top left point
     */
    var y: Double = .0

    /**
     * The targe size of the image
     */
    var size: Size? = null
}


/**
 * Represents an image in the specific platform. The loading
 * of the image is outside of the common code.
 *
 * Depending on the platform, it can rely on Image Element (like HTMLImageElement) or
 * on class loading mechanisms
 */
interface ImageHandler