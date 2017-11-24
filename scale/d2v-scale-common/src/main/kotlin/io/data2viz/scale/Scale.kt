package io.data2viz.scale

/*data class DomainToViz<out D, out V>(
        val domain: D,
        val range: V
)*/

interface Scale<D, out V> {

    val domain: List<D>
    val range: List<V>

    operator fun invoke(domainValue: D): V

    fun ticks(count: Int = 10): List<Double>
}