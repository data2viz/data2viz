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
        val EOL = EOL()
        var eol = false
        val regexp by lazy { Regex("\"\"") }

        fun token(): Token {
            if (I >= N) return EOF()
            if (eol) {
                eol = false
                return EOL
            }

            val j = I
            var c: Char

            if (text[j] == '"') {
                var i = j
                while (i++ < N) {
                    if (text[i] == '"') {
                        if (text[i + 1] != '"') break
                        ++i
                    }
                }
                I = i + 2
                c = text[i + 1]
                if (c == '\r') {
                    eol = true
                    if (text[i + 2] == '\n') ++I
                } else if (c == '\n') eol = true
                return TextToken(text.substring(j + 1, i).replace(regexp, "\""))

            }

            while (I < N) {
                var k = 1
                c = text[I++]
                if (c == '\n') {
                    eol = true
                } else if (c == '\r') {
                    eol = true; if (text[I] == '\n') {
                        ++I; ++k
                    }
                } else if (c != delimiterCode) {
                    continue
                }
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
