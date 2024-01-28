package src.main.kotlin

import src.main.kotlin.geometry.Angle
import src.main.kotlin.geometry.CartesianProduct
import src.main.kotlin.geometry.PlaneProjection
import src.main.kotlin.geometry.VectorNorm

class Plane(
    val planePoint: CartesianVector, planeNormal: CartesianVector
) : Surface {
    val normal = planeNormal / VectorNorm.instance(planeNormal)

    override fun surfaceNormal(point: CartesianVector): CartesianVector {
        val distance = PlaneProjection.pointToPlaneAbs(point, this)
        require(distance < 1e-6) { "Point is not on the plane. It is $distance away" }
        return normal
    }

    override fun intersectionPoint(ray: Ray): CartesianVector? {
        val perpendicularDistance = PlaneProjection.pointToPlaneAbs(ray.point, this)
        if (perpendicularDistance == 0f) return ray.point

        val perpendicularDisplacement: CartesianVector = PlaneProjection.pointToPlaneVector(ray.point, this)
        val cosineToPerpendicular = Angle.cosine(ray.direction, perpendicularDisplacement)

        if (cosineToPerpendicular == 0f) return null

        return ray.point + ray.direction * perpendicularDistance / cosineToPerpendicular
    }
}
