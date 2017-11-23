package io.data2viz.scale

data class DomainToRange<out D, out R>(
        val domain: D,
        val range: R
)

interface Scale<D, out R> {
    val domainsToRanges: List<DomainToRange<D, R>>

    operator fun invoke(domain: D): R

    //fun ticks(count: Int = 10): List<D>
}
