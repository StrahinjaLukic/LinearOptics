package src.main.kotlin.geometry

import src.main.kotlin.*

class RayProjection {
    companion object {
        fun projectionVector(point: CartesianVector, ray: Ray): CartesianVector {
            val rayPointDistanceVector = Distance.vector(point, ray.point)
            val longitudinalComponent = ray.direction * CartesianProduct.instance(ray.direction, rayPointDistanceVector)
            return rayPointDistanceVector - longitudinalComponent
        }

        fun projectionDistance(point: CartesianVector, ray: Ray): Float =
            VectorNorm.instance(projectionVector(point, ray))

        fun projectionPoint(point: CartesianVector, ray: Ray): CartesianVector = point + projectionVector(point, ray)
    }
}