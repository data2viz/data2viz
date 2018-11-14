package io.data2viz.scale


class IndexableDomain<D> : DiscreteDomain<D>{

    internal val index: MutableMap<D, Int> = HashMap()
    internal val _domain: MutableList<D> = arrayListOf()

    /**
     * The first element in domain will be mapped to the first element in the range, the second domain value to
     * the second range value, and so on.
     *
     * Domain values are stored internally in a map from stringified value to index; the resulting index is then used
     * to retrieve a value from the range. Thus, an ordinal scale’s values must be coercible to a string,
     * and the stringified version of the domain value uniquely identifies the corresponding range value.
     *
     * Setting the domain on an ordinal scale is optional if the unknown value is implicit (the default).
     * In this case, the domain will be inferred implicitly from usage by assigning each unique value passed
     * to the scale a new value from the range.
     *
     * Note that an explicit domain is recommended to ensure deterministic behavior, as inferring the domain
     * from usage will be dependent on ordering.
     */
    override var domain: List<D>
        get() = _domain.toList()
        set(value) {
            _domain.clear()
            index.clear()
            value.forEach {
                if (!index.containsKey(it)) {
                    _domain.add(it)
                    index.put(it, _domain.size - 1)
                }
            }
        }
}

/**
 * Unlike continuous scales, ordinal scales have a discrete domain and range.
 * For example, an ordinal scale might map a set of named categories to a set of colors, or determine the
 * horizontal positions of columns in a column chart.
 */
open class OrdinalScale<D, R>(range: List<R> = listOf(), val indexableDomain: IndexableDomain<D> = IndexableDomain()) : Scale<D,R>, DiscreteDomain<D> by indexableDomain {

    protected val _range: MutableList<R> = arrayListOf()

    init {
        _range.addAll(range.toList())
    }



    /**
     * If range is specified, sets the range of the ordinal scale to the specified array of values.
     * The first element in the domain will be mapped to the first element in range, the second domain value to
     * the second range value, and so on.
     * If there are fewer elements in the range than in the domain, the scale will reuse values from the start
     * of the range.
     */
    var range: List<R>
        get() = _range.toList()
        set(value) {
            require(value.isNotEmpty(), { "Range can't be empty." })
            _range.clear()
            _range.addAll(value.toList())
        }


    /**
     * The behavior when asking for a rangeValue with a given non-existant domainValue :
     * If unknown is null : add domainValue to the domain, then return a rangeValue (= scale implicit).
     * If unknown is not null : return unknown.
     */
    // TODO : change behavior
    private var _unknown: R? = null



    var unknown: R?
        get() = _unknown
        set(value) {
            _unknown = value
        }

    override operator fun invoke(domainValue: D): R {
        if (_unknown == null && !indexableDomain.index.containsKey(domainValue)) {
            indexableDomain._domain.add(domainValue)
            indexableDomain.index.put(domainValue, indexableDomain._domain.size - 1)
        }

        val index = indexableDomain.index[domainValue] ?: return _unknown ?: throw IllegalStateException()
        return when {
            _range.isEmpty() -> _unknown ?: throw IllegalStateException()
            else -> _range[index % _range.size]
        }
    }

}
