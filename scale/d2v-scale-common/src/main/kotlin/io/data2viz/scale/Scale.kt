package io.data2viz.scale


// TODO (DV-63)
/*data class DomainToViz<out D, out V>(
        val domain: D,
        val viz: V          /// ????
)*/

// TODO add more specific interfaces (Roundable ?)


/**
 * Generic signature of scales. A scale is defined by a list of Domain objects. 
 * Then at runtime, one can ask an R object for a specific value of domain.
 * The rules defining the returns of R from D depends a lot on the type and
 * implementation of the Scale.
 * 
 * a Domain object -> Range object
 */
interface Scale<D, out R> {
    val domain: List<D>
    operator fun invoke(domainValue: D): R
}

/**
 * A scale for which it is possible to define a range List 
 */
interface RangeableScale<D, R> : Scale<D, R> {
    val range: List<R>
}

/**
 * Indicates a scale for which the resulting R
 */
interface ClampableScale<D, out R> : Scale<D, R> {
    val clamp: Boolean
}

interface NiceableScale<D, R> : Scale<D, R> {
    fun nice(count: Int = 10)
}

interface InvertableScale<D, R> : Scale<D, R> {
    fun invert(rangeValue: R): D
}

/**
 * Can provide ticks from Domain D.
 */
interface Tickable<D>{
    fun ticks(count: Int = 10): List<D>
}