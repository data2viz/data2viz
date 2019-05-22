package io.data2viz.geo.projection.common


class ComposedProjectable(val a: Projectable, val b:Projectable): Projectable {
    override fun projectLambda(lambda: Double, phi: Double): Double {
        val aX = a.projectLambda(lambda, phi)
        val aY = a.projectPhi(lambda, phi)
        return b.projectLambda(aX, aY)
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        val aX = a.projectLambda(lambda, phi)
        val aY = a.projectPhi(lambda, phi)
        return b.projectPhi(aX, aY)
    }

}

class ComposedProjector(val a: Projector, val b:Projector): Projector  {
    override fun projectLambda(lambda: Double, phi: Double): Double {
        val aX = a.projectLambda(lambda, phi)
        val aY = a.projectPhi(lambda, phi)
        return b.projectLambda(aX, aY)
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        val aX = a.projectLambda(lambda, phi)
        val aY = a.projectPhi(lambda, phi)
        return b.projectPhi(aX, aY)
    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {
        val p = b.invert(lambda, phi)
        return a.invert(p[0], p[1])
    }
}
//
///**
// * Todo document
// */
//fun compose(a: Projectable, b: Projectable): Projectable {
//    if (a is Invertable && b is Invertable) {
//        return object : Projector {
//
//        }
//    } else {
//        return object : Projectable {
//
////            override fun project(point: DoubleArray) {
////                point[0] = a.projectLambda(point[0], point[1])
////                point[1] = a.projectPhi(point[0], point[1])
////                b.project(point)
////            }
//
//
//        }
//    }
//}
