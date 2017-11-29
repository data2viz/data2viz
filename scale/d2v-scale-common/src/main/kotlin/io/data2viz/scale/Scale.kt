package io.data2viz.scale


// TODO (DV-63)
/*data class DomainToViz<out D, out V>(
        val domain: D,
        val viz: V          /// ????
)*/

// TODO add more specific interfaces (Roundable ?)
// TODO clamp in Scale ?

interface Scale<D, out V> {
    val domain: List<D>
    operator fun invoke(domainValue: D): V
}

interface RangeableScale<D, V> : Scale<D, V> {
    val range: List<V>
}

interface ClampableScale<D, out V> : Scale<D, V> {
    val clamp: Boolean
}

interface NiceableScale<D, V> : Scale<D, V> {
    fun nice(count: Int = 10)
}

interface InvertableScale<D, V> : Scale<D, V> {
    fun invert(rangeValue: V): D
}

interface TickableScale<D, V>: Scale<D, V> {
    fun ticks(count: Int = 10): List<D>
}