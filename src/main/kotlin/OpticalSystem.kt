package src.main.kotlin

import src.main.kotlin.geometry.VectorNorm
import kotlin.math.pow
import kotlin.math.sqrt

class OpticalSystem(private val opticalElements: List<OpticalElement>) : OpticalElement {

    override operator fun invoke(ray: Ray): Ray {
        var rayOut = ray
        opticalElements.forEach { rayOut = it(rayOut) }
        return rayOut
    }

    companion object {
        /**
         * Factory function for the creation of a biconvex lens.
         *
         * A biconvex lens consists of two spherical sections, with axes pointing in the opposite directions, both
         * sections sharing the common intersection plane.
         *
         * @param centerPoint Point at the center of the plane intersecting both spherical sections.
         * @param axis Direction of the lens axis. Both spherical sections will be directed along this axis, one in the
         * positive and the other in the negative direction.
         * @param radiusForward Spherical radius of the forward-directed section.
         * @param radiusBackward Spherical radius of the backward-directed section.
         * @param sectionRadius The common sectionRadius of both spherical sections.
         * @param refractionIndex
         */
        fun biconvexLens(
            centerPoint: CartesianVector,
            axis: CartesianVector,
            radiusForward: Float,
            radiusBackward: Float,
            sectionRadius: Float,
            refractionIndex: Float
        ): OpticalSystem {
            require(VectorNorm.isNormed(axis))

            val dForward = sqrt(radiusForward.pow(2) - sectionRadius.pow(2))
            val dBackward = sqrt(radiusBackward.pow(2) - sectionRadius.pow(2))

            val sectionForward = SphericalSection(centerPoint - axis * dForward, radiusForward, axis, sectionRadius)
            val sectionBackward = SphericalSection(centerPoint + axis * dBackward, radiusBackward, -axis, sectionRadius)

            return OpticalSystem(
                listOf(
                    OpticalSurface.makeSphericalRefractor(sectionForward, refractionIndex),
                    OpticalSurface.makeSphericalRefractor(sectionBackward, refractionIndex)
                )
            )
        }
    }
}
