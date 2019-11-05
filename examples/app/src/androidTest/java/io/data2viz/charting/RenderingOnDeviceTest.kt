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

package io.data2viz.charting

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import io.data2viz.viz.*

import org.junit.Test
import org.junit.runner.RunWith

import java.io.File
import java.io.FileOutputStream

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4::class)
class RenderingOnDeviceTest {

    @Test
    fun renderImages() {
//        TestRenderings.toRender.forEach {
//            renderNode(it.first, it.second)
//        }
    }

    private fun renderNode(rendereredNode: Node, name: String) {
        val canvasSize = 500

        val bitmap = Bitmap.createBitmap(canvasSize, canvasSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val androidCanvasRenderer = AndroidCanvasRenderer(
                InstrumentationRegistry.getContext(), Viz(), canvas)

        val viz = viz {
            renderer = androidCanvasRenderer
            add(rendereredNode)
        }
        viz.render()
        val appContext = InstrumentationRegistry.getTargetContext()
        val dir = getPrivateAlbumStorageDir(appContext, "data2canvas")
        val file = File(dir, "$name-android.png")
        val f = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, f)
        f.close()
    }
}

fun getPrivateAlbumStorageDir(context: Context, dirName: String): File? {
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), dirName)
    if (!file.mkdirs()) Log.e("viz", "Directory not created")
    return file
}
