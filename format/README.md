# d2v-format

d2v-format is a multiplaform library (js, jvm) for number formating.

Its purpose is to format numbers for human consumption. It provides :

* managing of rounding errors,
* groupings digits for large numbers (e.g, 42,000)
* providing scientific or metric notation (4.2e+4, 42k)
* fixed precicision for currencies ($3.50)
* rounding numerical results
* locales management  

It is based of [d3-format](https://github.com/d3/d3-format) which itself modeled after Python 3’s 
[format specification mini-language](https://docs.python.org/3/library/string.html#format-specification-mini-language) ([PEP 3101](https://www.python.org/dev/peps/pep-3101/)).

## Format Types 
This is the most important formattting parameter. It gives the global transformation rules. 

Based numbers: the number is rounded and then formatted as a [BINARY], [OCTAL], [HEXADECIMAL],[HEXADECIMAL_UPPER].



There are two ways of defining the formatting options:
   1. using the format specifier 
   2. using the typed API
   
## Format Specifier

The format specifier is a mini language that allows you to create a FormatSpec from one
string definition. 

Here are some examples:

```kotlin
formatter(".0%")(0.123)                    // rounded percentage, "12%" 
formatter("+20")(42.0)                     // localized fixed-point currency, "(£3.50)" 
formatter(".^20")(42.0)                    // space-filled and signed, "                 +42" 
formatter(".2s")(42e6)                     // dot-filled and centered, ".........42........." 
formatter("#x")(48879.0)                   // SI-prefix with two significant digits, "42M" 
formatter(",.2r")(4223.0)                  // prefixed lowercase hexadecimal, "0xbeef" 
Locales.en_GB().formatter("($.2f")(-3.5)   // grouped thousands with two significant digits, "4,200" 
```

The general form of a specifier is:

```
[​[fill]align][sign][symbol][0][width][,][.precision][type]
```

The *fill* can be any character. The presence of a fill character is signaled by the *align* character following it, which must be one of the following:

* `>` - Forces the field to be right-aligned within the available space. (Default behavior).
* `<` - Forces the field to be left-aligned within the available space.
* `^` - Forces the field to be centered within the available space.
* `=` - like `>`, but with any sign and symbol to the left of any padding.

The *sign* can be:

* `-` - nothing for zero or positive and a minus sign for negative. (Default behavior.)
* `+` - a plus sign for zero or positive and a minus sign for negative.
* `(` - nothing for zero or positive and parentheses for negative.
* ` ` (space) - a space for zero or positive and a minus sign for negative.

The *symbol* can be:

* `$` - apply currency symbols per the locale definition.
* `#` - for binary, octal, or hexadecimal notation, prefix by `0b`, `0o`, or `0x`, respectively.

The *zero* (`0`) option enables zero-padding; this implicitly sets *fill* to `0` and *align* to `=`. The *width* defines the minimum field width; if not specified, then the width will be determined by the content. The *comma* (`,`) option enables the use of a group separator, such as a comma for thousands.

Depending on the *type*, the *precision* either indicates the number of digits that follow the decimal point (types `f` and `%`), or the number of significant digits (types `​`, `e`, `g`, `r`, `s` and `p`). If the precision is not specified, it defaults to 6 for all types except `​` (none), which defaults to 12. Precision is ignored for integer formats (types `b`, `o`, `d`, `x`, `X` and `c`). See [precisionFixed](#precisionFixed) and [precisionRound](#precisionRound) for help picking an appropriate precision.

The available *type* values are:

* `e` - exponent notation.
* `f` - fixed point notation.
* `g` - either decimal or exponent notation, rounded to significant digits.
* `r` - decimal notation, rounded to significant digits.
* `s` - decimal notation with an [SI prefix](#locale_formatPrefix), rounded to significant digits.
* `%` - multiply by 100, and then decimal notation with a percent sign.
* `p` - multiply by 100, round to significant digits, and then decimal notation with a percent sign.
* `b` - binary notation, rounded to integer.
* `o` - octal notation, rounded to integer.
* `d` - decimal notation, rounded to integer.
* `x` - hexadecimal notation, using lower-case letters, rounded to integer.
* `X` - hexadecimal notation, using upper-case letters, rounded to integer.
* `c` - converts the integer to the corresponding unicode character before printing.
* `​` (none) - like `g`, but trim insignificant trailing zeros.

The type `n` is also supported as shorthand for `,g`. For the `g`, `n` and `​` (none) types, 
decimal notation is used if the resulting string would have *precision* or fewer digits; otherwise, 
exponent notation is used. For example:

The **main advantage** is its concision. The **problem** is that you need to know exactly the mini language
to use it (or spend some time reading the documentation). If you use it every day it may not be a problem. 
Otherwise, you can use the typed API.

## Specifying the formatter with the typed API

The other way to create a formatter is to use the typed API.

Examples:

```kotlin
formatter (Type.PERCENT, precision = 0)(0.123)                         // rounded percentage, "12%"                             
formatter(sign = Sign.PLUS, width = 20)(42.0)                          // space-filled and signed, "                 +42"       
formatter(fill = '.', align = Align.CENTER, width = 20)(42.0)          // dot-filled and centered, ".........42........."       
formatter(Type.DECIMAL_WITH_SI, precision = 2)(42e6)                   // SI-prefix with two significant digits, "42M"          
formatter(Type.HEX_LOWERCASE, symbol = Symbol.NUMBER_BASE)(48879.0)    // prefixed lowercase hexadecimal, "0xbeef"              
formatter(Type.DECIMAL, group = true, precision = 2)(4223.0)           // grouped thousands with two significant digits, "4,200"
```
It's more verbose but the developper benefits from the IDE code completion (and documentation). It
is also easier to understand the parameter.