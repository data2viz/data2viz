
package io.data2viz.format

import kotlin.math.max


/**
 * The typed safe definition of a Formatter. All properties have a default value, allowing
 * to use named parameter to only define what is specific to the current formatter.
 *
 * @property fill The char used to fill the spaces (when width is larger than the representation).
 * by default a `space`.
 *
 * @property align The alignment of the number inside the available space (by default [Align.RIGTH])
 *
 * @property sign Defines how positive and negative numbers are represented.
 *
 * @property symbol Adds [Symbol.CURRENCY] or [Symbol.NUMBER_BASE] to the number representation.
 *
 * @property zero This option enables zero-padding; this implicitly sets fill to `0` and aligne to
 * [Align.RIGHT_WITHOUT_SIGN]. The width defines the minimum field width; if not specified, then the
 * width will be determined by the content.
 *
 * @property groupSeparation This option enables the use of a group separator, such as a comma for thousands.
 *
 * @property precision on the type, the precision either indicates the number of digits that
 * follow the decimal point (types f and %), or the number of significant digits (types ​, e, g, r, s and p).
 * If the precision is not specified, it defaults to 6 for all types except ​ (none), which defaults to 12.
 * Precision is ignored for integer formats (types b, o, d, x, X and c). See precisionFixed and precisionRound
 * for help picking an appropriate precision.
 *
 * @property type The type of number representation.
 */
data class FormatSpec(
        val fill: Char = ' ',
        val align: Align = Align.RIGTH,
        val sign: Sign = Sign.MINUS,
        val symbol: Symbol? = null,
        val zero: Boolean = false,
        val width: Int? = null,
        val groupSeparation: Boolean = false,
        val precision: Int? = null,
        val type: Type = Type.NONE) {

    /**
     * Usable for debugging, represents the format specification as a String specifier.
     */
    override fun toString(): String =
            "$fill$align$sign${if (symbol == null) "" else symbol.c}${if (zero) "0" else ""}${if (width == null) "" else max(1, width)}${if (groupSeparation) "," else ""}${if (precision == null) "" else "." + max(0, precision)}${type.toString()}"
}

/**
 * Returns a new FormatSpec for the given string specifier. The returned function
 * takes a number as the only argument, and returns a string representing the formatted
 * number.
 *
 * The general form of a specifier is:
 *
 * `[​[fill]align][sign][symbol][0][width][,][.exponent][type]`
 *
 * The fill can be any character. The presence of a fill character is signaled by the align
 * character following it, which must be one of the following:
 * - `>` Forces the field to be right-aligned within the available space. (Default behavior).
 * - `<` Forces the field to be left-aligned within the available space.
 * - `^` Forces the field to be centered within the available space.
 * - `=` like >, but with any sign and symbol to the left of any padding.
 *
 * The sign can be:
 * - `-`  nothing for zero or positive and a minus sign for negative. (Default behavior.)
 * - `+`  a plus sign for zero or positive and a minus sign for negative.
 * - `(`  nothing for zero or positive and parentheses for negative.
 * - ` ` (space) a space for zero or positive and a minus sign for negative.
 *
 * The symbol can be:
 * - `$` apply currency symbols per the locales definition.
 * - `#` for binary, octal, or hexadecimal notation, prefix by 0b, 0o, or 0x, respectively.
 *
 * The zero (0) option enables zero-padding; this implicitly sets fill to 0 and align to =.
 * The width defines the minimum field width; if not specified, then the width will be determined
 * by the content.
 * The groupSeparation (,) option enables the use of a grouping separator, such as a
 * groupSeparation for thousands.
 *
 * Depending on the type, the exponent either indicates the number of digits that
 * follow the coefficient point (types f and %), or the number of significant digits
 * (types ​"", e, g, r, s and p). If the exponent is not specified, it defaults to 6 for all types except ​ (none), which defaults to 12.
 * Precision is ignored for integer formats (types b, o, d, x, X and c).
 * See precisionFixed and precisionRound for help picking an appropriate exponent.
 *
 * The available type values are:
 *
 * - `e` exponent notation.
 * - `f` fixed point notation.
 * - `g` either coefficient or exponent notation, rounded to significant digits.
 * - `r` coefficient notation, rounded to significant digits.
 * - `s` coefficient notation with an SI prefix, rounded to significant digits.
 * - `%` multiply by 100, and then coefficient notation with a percent sign.
 * - `p` multiply by 100, round to significant digits, and then coefficient notation with a percent sign.
 * - `b` binary notation, rounded to integer.
 * - `o` octal notation, rounded to integer.
 * - `d` coefficient notation, rounded to integer.
 * - `x` hexadecimal notation, using lower-case letters, rounded to integer.
 * - `X` hexadecimal notation, using upper-case letters, rounded to integer.
 * - `c` converts the integer to the corresponding unicode character before printing.
 * - ` ​` (none) - like g, but trim insignificant trailing zeros.
 *
 * The type n is also supported as shorthand for ,g. For the g, n and ​ (none) types,
 * coefficient notation is used if the resulting string would have exponent or fewer digits;
 * otherwise, exponent notation is used. For example:
 *
 * ```kotlin
 * formatter(".2")(42);  // "42"
 * formatter(".2")(4.2); // "4.2"
 * formatter(".1")(42);  // "4e+1"
 * formatter(".1")(4.2); // "4"
 * ```
 */
fun specify(specifier: String): FormatSpec {
    var fill = ' '
    var align = Align.RIGTH
    var sign = Sign.MINUS
    var symbol: Symbol? = null
    var zero: Boolean
    var width: Int? = null
    var groupSeparation: Boolean
    var precision: Int? = null
    var type: Type = Type.NONE

    if (!formatRE.matches(specifier)) throw IllegalArgumentException("invalid format: " + specifier)

    val match = formatRE.find(specifier)!!.groupValues

    fun readType(string: String) {
        // The "n" type is an alias for ",g".
        if (string == "n") {
            groupSeparation = true
            type = Type.DECIMAL_OR_EXPONENT
        } else {
            type = Type.values().firstOrNull { it.c == match[9] } ?: Type.NONE
        }
    }

    if (match[1].isNotEmpty()) fill = match[1][0]
    if (match[2].isNotEmpty()) align = Align.values().first { it.c == match[2] }
    if (match[3].isNotEmpty()) sign = Sign.values().first { it.c == match[3] }
    if (match[4].isNotEmpty()) symbol = Symbol.values().first { it.c == match[4] }
    zero = (match[5] == "0")
    if (match[6].isNotEmpty() && match[6].toIntOrNull() != null) width = match[6].toInt()
    groupSeparation = (match[7] == ",")
    if (match[8].length > 1 && match[8].substring(1).toIntOrNull() != null) precision = match[8].substring(1).toInt()

    readType(match[9])


    // If zero fill is specified, padding goes after sign and before digits.
    if (zero || (fill == '0' && align == Align.RIGHT_WITHOUT_SIGN)) {
        zero = true
        fill = '0'
        align = Align.RIGHT_WITHOUT_SIGN
    }

    return FormatSpec(fill, align, sign, symbol, zero, width, groupSeparation, precision, type)
}

private val formatRE: Regex = Regex("^(?:(.)?([<>=^]))?([+\\-( ])?([$#])?(0)?(\\d+)?(,)?(\\.\\d+)?([a-z%])?$", RegexOption.IGNORE_CASE)


/**
 * Instanciate a [FormatSpec].
 * @see FormatSpec for a complete description of parameters.
 */
fun specify(
        type: Type = Type.NONE,
        fill: Char = ' ',
        align: Align = Align.RIGTH,
        sign: Sign = Sign.MINUS,
        symbol: Symbol? = null,
        zero: Boolean = false,
        width: Int? = null,
        groupSeparation: Boolean = false,
        precision: Int? = null
)
        = FormatSpec(fill, align, sign, symbol, zero, width, groupSeparation, precision, type)

/**
 * Indicates the type of symbol that should be displayed. It can be a currency
 * (depending of current locale) or an Number Base indication for binary, octal, or
 * hexadecimal notation (prefix by 0b, 0o, or 0x, respectively).
 */
enum class Symbol(internal val c: String) {
    /**
     * The local currency symbol should be displayed. Depending of the locale, the
     * symbol can be placed before of after the number.
     */
    CURRENCY("$"),
    /**
     * Displays the prefix depending on number base (binary, octal, hexadecimal).
     */
    NUMBER_BASE("#")
    ;

    /**
     * @suppress
     */
    override fun toString() = c
}


/**
 * Defines the type of number representation
 * [EXPONENT], [FIXED_POINT],
 * [PERCENT], [PERCENT_ROUNDED],
 * [DECIMAL], [DECIMAL_ROUNDED], [DECIMAL_WITH_SI], [DECIMAL_OR_EXPONENT],
 * [BINARY], [OCTAL], [HEX_LOWERCASE], [HEX_UPPERCASE],
 * [CHAR].
 * Defaults to [NONE]
 */
enum class Type(internal val c: String) {

    /**
     * like [DECIMAL_OR_EXPONENT], but trim insignificant trailing zeros.
     */
    NONE(" "),

    /**
     * decimal notation, rounded to significant digits.
     */
    DECIMAL("r"),

    /**
     * decimal notation, rounded to integer.
     */
    DECIMAL_ROUNDED("d"),

    /**
     * decimal notation with an [SI prefix][Locale.formatPrefix], rounded to significant digits.
     */
    DECIMAL_WITH_SI("s"),

    /**
     * either decimal or exponent notation, rounded to significant digits.
     */
    DECIMAL_OR_EXPONENT("g"),

    /**
     * Exponent notation
     */
    EXPONENT("e"),

    /**
     * Fixed point notation
     */
    FIXED_POINT("f"),

    /**
     * multiply by 100, and then decimal notation with a percent sign.
     */
    PERCENT("%"),

    /**
     * multiply by 100, round to significant digits, and then decimal notation with a percent sign
     */
    PERCENT_ROUNDED("p"),

    /**
     * binary notation, rounded to integer.
     */
    BINARY("b"),

    /**
     * octal notation, rounded to integer.
     */
    OCTAL("o"),

    /**
     * hexadecimal notation, using lower-case letters, rounded to integer.
     */
    HEX_LOWERCASE("x"),

    /**
     * hexadecimal notation, using upper-case letters, rounded to integer.
     */
    HEX_UPPERCASE("X"),

    /**
     * converts the integer to the corresponding unicode character before printing.
     */
    CHAR("c");

    /**
     * @suppress
     */
    override fun toString() = if (this == NONE) "" else c
}


/**
 * Check if it is a number based type (binary, octal, hex ie: boxX)
 */
internal val Type?.isNumberBase: Boolean
    get() =
        (this != null &&
                (this == Type.BINARY ||
                        this == Type.OCTAL ||
                        this == Type.HEX_UPPERCASE ||
                        this == Type.HEX_LOWERCASE))

/**
 * Check if it is a percent type (%p)
 */
internal val Type?.isPercent: Boolean
    get() =
        (this != null && (
                this == Type.PERCENT ||
                        this == Type.PERCENT_ROUNDED))

internal val Type?.maybeSuffix: Boolean
    get() =
        (this != null && (
                this == Type.DECIMAL_ROUNDED ||
                        this == Type.EXPONENT ||
                        this == Type.FIXED_POINT ||
                        this == Type.DECIMAL_OR_EXPONENT ||
                        this == Type.PERCENT_ROUNDED ||
                        this == Type.DECIMAL ||
                        this == Type.DECIMAL_WITH_SI ||
                        this == Type.PERCENT
                ))

internal val gprs = listOf(
        Type.DECIMAL_OR_EXPONENT,
        Type.PERCENT_ROUNDED,
        Type.DECIMAL,
        Type.DECIMAL_WITH_SI
)


/**
 * Define how positive and negative number should be rendered. [MINUS] by default.
 */
enum class Sign(internal val c: String) {
    /**
     * nothing for zero or positive and a minus sign for negative. (Default behavior.)
     */
    MINUS("-"),
    /**
     * a plus sign for zero or positive and a minus sign for negative.
     */
    PLUS("+"),
    /**
     * nothing for zero or positive and parentheses for negative.
     */
    PARENTHESES("("),
    /**
     * a space for zero or positive and a minus sign for negative.
     */
    SPACE(" ");

    /**
     * @suppress
     */
    override fun toString() = c
}

/**
 * Alignment of the number. By default [RIGTH] but can be
 * [LEFT], [CENTER] or [RIGHT_WITHOUT_SIGN]. The padding is filled by [FormatSpec.fill]
 * property.
 */
enum class Align(val c: String) {
    /**
     * Forces the field to be right-aligned within the available space. (Default behavior).
     */
    RIGTH(">"),
    /**
     * Forces the field to be left-aligned within the available space.
     */
    LEFT("<"),
    /**
     * Forces the field to be centered within the available space.
     */
    CENTER("^"),

    /**
     * like [RIGTH], but with any sign and symbol to the left of any padding.
     */
    RIGHT_WITHOUT_SIGN("=");

    /**
     * @suppress
     */
    override fun toString() = c
}
