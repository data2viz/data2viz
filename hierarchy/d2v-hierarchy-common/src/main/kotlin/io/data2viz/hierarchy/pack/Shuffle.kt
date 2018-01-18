//package io.data2viz.hierarchy.pack
//
//import io.data2viz.hierarchy.PackNode
//import io.data2viz.hierarchy.packNode
//
//internal data class HeadTail<D>(
//    var head:PackNode<D>?,
//    var tail:PackNode<D>?
//)
//
//internal fun <D> shuffle(list:List<PackNode<D>>): HeadTail<D> {
//    val i = 0
//    val array = list.toMutableList()
//    var n = array.size
//    var head:PackNode<D>? = null
//    var node = head
//
//    while (n != 0) {
//        val next = packNode(array[n - 1])
//        if (node != null) {
//            node.next = next
//            node = next
//        }
//        else {
//            head = next
//            node = head
//        }
//        n -= 1
//        array[i] = array[--n]
//    }
//
//    return HeadTail(head!!, node!!)
//}