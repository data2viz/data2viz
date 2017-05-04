package io.data2viz.dsv

import io.data2viz.dsv.Dsv.Token.*



class Dsv(val delimiterCode: Char = ',') {


    sealed class Token {
        class EOF : Token()
        class EOL : Token()
        class TextToken(val content: String) : Token()
    }


    fun parse() {}
    fun parseRows(text: String): MutableList<List<String>> {

        val rows = mutableListOf<List<String>>()
        val N = text.length
        var I = 0
        var n = 0
        var eol = false

        fun token(): Token {
            if (I >= N) return EOF()
            if (eol) {
                eol = false
                return EOL()
            }

            val j = I
            var c: Char

            //todo quotes

            //common case: find next delimiter or newline
            while (I < N) {
                var k = 1
                c = text[I++]
                println(c)
                if (c == '\n') { eol = true }
                else if (c == '\r') { eol = true; if (text[I] == '\n') { ++I; ++k } }
                else if (c != delimiterCode) { continue }
                return TextToken(text.substring(j, I - k))
            }

            // special case: last token before EOF
            return TextToken(text.substring(j))
        }

        var t = token()
        while (t !is EOF) {
            val row = mutableListOf<String>()
            while (t is TextToken) {
                row += t.content
                t = token()
            }
            rows += row
            t = token()
        }
        return rows
    }

    fun format() {}
    fun formatRows() {}

}
