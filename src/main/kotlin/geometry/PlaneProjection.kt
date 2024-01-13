package src.main.kotlin.geometry

import src.main.kotlin.*
import kotlin.math.abs

class PlaneProjection {
    companion object {
        fun pointToPlaneSigned(point: CartesianVector, plane: Plane): Float {
            val displacement = plane.planePoint - point
            return CartesianProduct()(plane.normal, displacement)
        }

        fun pointToPlaneAbs(point: CartesianVector, plane: Plane): Float {
            return abs(pointToPlaneSigned(point, plane))
        }

        fun pointToPlaneVector(point: CartesianVector, plane: Plane): CartesianVector {
            return plane.normal * pointToPlaneSigned(point, plane)
        }
    }
}