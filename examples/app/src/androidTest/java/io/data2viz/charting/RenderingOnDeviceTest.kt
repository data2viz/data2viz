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
                InstrumentationRegistry.getContext(), canvas)

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
