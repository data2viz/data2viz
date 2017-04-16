

private val hexStr = "0123456789abcdef"

class Color {

    var _alpha: Float = 1.0f
    var rgb = 0xffffff

    var r:Int
        get() = (rgb shr 16) and 0xff
        set(value) {
            if(value > 255) error("r should be less or equal to 255")
            if(value < 0)   error("r should be greater or equal to 0")
            rgb = (rgb and 0x00ffff) + (value shl 16)
        }

    var g:Int
        get() = (rgb shr 8) and 0xff
        set(value) {
            if(value > 255) error("g should be less or equal to 255")
            if(value < 0)   error("g should be greater or equal to 0")
            rgb = (rgb and 0xff00ff) + (value shl 8)
        }

    var b:Int
        get() = rgb and 0xff
        set(value) {
            if(value > 255) error("b should be less or equal to 255")
            if(value < 0)   error("b should be greater or equal to 0")
            rgb = (rgb and 0xffff00) + value
        }

    var alpha: Number
        get() = _alpha
        set(value) {
            _alpha = value.toFloat()
        }

    fun rgba(r: Number, g: Number, b: Number, a: Number) {
        this.r = r.toInt()
        this.g = g.toInt()
        this.b = b.toInt()
        alpha = a.toFloat()
    }

    val rgbHex: String
        get() {
            return "#" +
                    hexStr[(rgb shr 20) and 0xf] +
                    hexStr[(rgb shr 16) and 0xf] +
                    hexStr[(rgb shr 12) and 0xf] +
                    hexStr[(rgb shr 8) and 0xf] +
                    hexStr[(rgb shr 4) and 0xf] +
                    hexStr[rgb and 0xf]
        }
}
