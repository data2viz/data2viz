

package io.data2viz.test


import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun Double.toFixed(): String {

    return if (kotlin.math.abs(this - kotlin.math.round(this)) < 1e-6)
        kotlin.math.round(this).toString().dropLast(2)
    else NSString.stringWithFormat("%.6f", this )

}


actual annotation class JsName actual constructor(actual val name: String)