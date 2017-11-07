package io.data2viz.shape

data class StackSpace(
        val from: Double,
        val to: Double
)

data class StackParams<T>(
        val stackedValues: MutableList<StackSpace>,
        val data: T
)

fun <T> stack(init: StackGenerator<T>.() -> Unit) = StackGenerator<T>().apply(init)
class StackGenerator<T> {


    /*
    var keys = constant([]),
      order = orderNone,
      offset = offsetNone,
      value = stackValue;
     */

    var values: (T) -> Array<Double> = const(arrayOf(.0))

    fun stack(data: Array<T>): Array<StackParams<T>> {
        val size = data.size
        val ret = mutableListOf<StackParams<T>>()
        val sums = Array<Double>(size, { .0 })

        data.forEach { element ->
            val stackedValues = mutableListOf<StackSpace>()
            val stack = StackParams<T>(stackedValues, element)
            ret.add(stack)
        }
        data.forEachIndexed { index1, element ->
            val values = values(element)
            values.forEachIndexed { index2, value ->
                val stack = ret[index2]
                stack.stackedValues.add(StackSpace(sums[index1], sums[index1] + value))
                sums[index1] += value
            }
        }

        return ret.toTypedArray()
    }
}