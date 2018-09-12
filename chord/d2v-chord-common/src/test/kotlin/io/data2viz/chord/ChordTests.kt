package io.data2viz.chord

import io.data2viz.test.TestBase
import kotlin.test.Test

data class FromTo(
    val from:Double,
    val to:Double
)

class ChordTests : TestBase() {

    val data = listOf(0, 1, 2, 3)
    val groupsSortedNatural = listOf(3, 1, 0, 2)
    val subGroupsSortedNatural = listOf(3, 1, 0, 2)

    val angles = listOf(
        listOf(
            FromTo(.0, 0.7524114405347554),
            FromTo(0.7524114405347554, 1.1212972499192688),
            FromTo(1.1212972499192688, 1.6815060519074008),
            FromTo(1.6815060519074008, 1.8617078065173112)
        ),
        listOf(
            FromTo(1.8617078065173112, 1.9842927518603848),
            FromTo(1.9842927518603848, 2.6156272115257897),
            FromTo(2.6156272115257897, 2.7450608288536893),
            FromTo(2.7450608288536893, 3.1327961941597415)
        ),
        listOf(
            FromTo(3.1327961941597415, 3.6360793372648263),
            FromTo(3.6360793372648263, 4.650499605108971),
            FromTo(4.650499605108971, 5.1588092964598),
            FromTo(5.1588092964598, 5.664291554422397)
        ),
        listOf(
            FromTo(5.664291554422397, 5.727940221584126),
            FromTo(5.727940221584126, 5.790143756125205),
            FromTo(5.790143756125205, 5.8492056980126925),
            FromTo(5.8492056980126925, 6.283185307179586)
        )
    )
    val matrix = arrayOf(
        arrayOf(11975, 5871, 8916, 2868),
        arrayOf(1951, 10048, 2060, 6171),
        arrayOf(8010, 16145, 8090, 8045),
        arrayOf(1013, 990, 940, 6907)
    )

    @Test
    fun testChords() {
        val chordLayout = ChordLayout<Int>()
        val chords = chordLayout.chord(data, { from, to -> matrix[from][to].toDouble() })

        // size is 4 + 3 + 2 + 1
        val chordsSize = (0..data.size).sum()

        chords.chords.size shouldBe chordsSize
        chords.chords.forEach { chord ->
            chord.source.startAngle shouldBeClose angles[chord.source.index][chord.source.subIndex].from
            chord.source.endAngle shouldBeClose angles[chord.source.index][chord.source.subIndex].to
            chord.target.startAngle shouldBeClose angles[chord.target.index][chord.target.subIndex].from
            chord.target.endAngle shouldBeClose angles[chord.target.index][chord.target.subIndex].to
        }

        chords.groups.size shouldBe 4
        chords.groups.forEach { group ->
            group.startAngle shouldBeClose angles[group.index][0].from
            group.endAngle shouldBeClose angles[group.index][3].to
        }
    }

    @Test
    fun testSorting() {
        val chordLayout = ChordLayout<Int>()
        chordLayout.sortGroups = naturalOrder()

        val chords = chordLayout.chord(data, { from, to -> matrix[from][to].toDouble() })

        groupsSortedNatural.forEachIndexed { i, index ->
            val nextIndex = groupsSortedNatural[(i + 1) % groupsSortedNatural.size]
            ((chords.groups[index].endAngle)%6.283185307179585) shouldBeClose chords.groups[nextIndex].startAngle
        }
    }

    @Test
    fun testNoSorting() {
        val chordLayout = ChordLayout<Int>()
        chordLayout.sortGroups = null

        val chords = chordLayout.chord(data, { from, to -> matrix[from][to].toDouble() })

        data.forEachIndexed { i, index ->
            val nextIndex = data[(i + 1) % data.size]
            ((chords.groups[index].endAngle)%6.283185307179585) shouldBeClose chords.groups[nextIndex].startAngle
        }
    }
}