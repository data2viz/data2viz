package io.data2viz.geo

abstract class ComposedProjector : ProjectableInvertable {


    override fun invert(lambda: Double, phi: Double): DoubleArray = activeProjector.invert(lambda, phi)

    override fun project(lambda: Double, phi: Double): DoubleArray = activeProjector.project(lambda, phi)

    override fun projectLambda(lambda: Double, phi: Double): Double = activeProjector.projectLambda(lambda, phi)

    override fun projectPhi(lambda: Double, phi: Double): Double = activeProjector.projectPhi(lambda, phi)

    abstract val activeProjector: ProjectableInvertable
}

abstract class ConditionalProjector() : ComposedProjector() {

    abstract val baseProjector: ProjectableInvertable
    abstract val nestedProjector: ProjectableInvertable
    abstract val isNeedUseBaseProjector: Boolean


    override val activeProjector: ProjectableInvertable
        get() = if (isNeedUseBaseProjector) {
            baseProjector
        } else {
            nestedProjector
        }
}