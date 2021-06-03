/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

public class Dsv(

    private val delimiterCode: Char = ',') {

    internal sealed class Token {
        class EOF : Token()
        class EOL : Token()
        class TextToken(val content: String) : Token()
    }

    public fun parseRows(text: String): List<List<String>> {

        val rows = mutableListOf<List<String>>()
        val N = text.length
        var I = 0
        var n = 0
        val EOL = EOL()
        var eol = false
        var eof = false
        val regexp by lazy { Regex("\"\"") }

        fun token(): Token {
            if (eof || I >= N) return EOF()
            if (eol) {
                eol = false
                return EOL
            }

            val j = I
            var c: Char

//            var i, j = I, c;
//            if (text.charCodeAt(j) === QUOTE) {
//                while (I++ < N && text.charCodeAt(I) !== QUOTE || text.charCodeAt(++I) === QUOTE);
//                if ((i = I) >= N) eof = true;
//                else if ((c = text.charCodeAt(I++)) === NEWLINE) eol = true;
//                else if (c === RETURN) { eol = true; if (text.charCodeAt(I) === NEWLINE) ++I; }
//                return text.slice(j + 1, i - 1).replace(/""/g, "\"");
//            }

            // Unescape quotes
            if (text[j] == '"') {
                while (I++ < N && text[I] != '"' || (++I < N && text[I] == '"')) {}

                // Find next delimiter or newline
                val i = I
                if (I >= N) eof = true
                else {
                    c = text[I++]
                    if (c == '\n') eol = true
                    else if (c == '\r') {
                        eol = true
                        if (text[I] == '\n') ++I
                    }
                }
                return TextToken(text.substring(j + 1, i - 1).replace(regexp, "\""))
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
        return rows.toList()
    }

}
