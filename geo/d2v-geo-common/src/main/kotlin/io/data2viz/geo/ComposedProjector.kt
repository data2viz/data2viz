package io.data2viz.geo

abstract class ComposedProjector : Projector {


    override fun invert(lambda: Double, phi: Double): DoubleArray = activeProjector.invert(lambda, phi)

    override fun project(lambda: Double, phi: Double): DoubleArray = activeProjector.project(lambda, phi)

    override fun projectLambda(lambda: Double, phi: Double): Double = activeProjector.projectLambda(lambda, phi)

    override fun projectPhi(lambda: Double, phi: Double): Double = activeProjector.projectPhi(lambda, phi)

    abstract val activeProjector: Projector
}

abstract class ConditionalProjector() : ComposedProjector() {

    abstract val baseProjector: Projector
    abstract val nestedProjector: Projector
    abstract val isNeedUseBaseProjector: Boolean


    override val activeProjector: Projector
        get() = if (isNeedUseBaseProjector) {
            baseProjector
        } else {
            nestedProjector
        }
}