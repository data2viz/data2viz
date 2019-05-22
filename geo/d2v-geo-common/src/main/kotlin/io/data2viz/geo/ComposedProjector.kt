package io.data2viz.geo

abstract class ComposedProjector : ProjectableInvertable {


    override fun invert(x: Double, y: Double): DoubleArray = activeProjector.invert(x, y)

    override fun project(x: Double, y: Double): DoubleArray = activeProjector.project(x, y)

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