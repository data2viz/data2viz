package io.data2viz.path

import org.junit.Test
import kotlin.test.assertTrue
import kotlin.test.fail


class PathTests {

    @Test
    fun math(){
        assertTrue ("Pi should be between 3 and 4") { 3.14 < Math.PI && Math.PI < 3.15 }
    }
}


