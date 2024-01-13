package src.main.kotlin

import src.main.kotlin.geometry.CartesianProduct
import src.main.kotlin.geometry.ClosestDistance
import src.main.kotlin.geometry.VectorNorm

class Plane(
    val planePoint: CartesianVector, planeNormal: CartesianVector
) : Surface {
    val normal = planeNormal / VectorNorm()(planeNormal)

    override fun intersection(ray: Ray): LocalSurfaceNormal?
    {
        val delta: CartesianVector = planePoint - ray.point
        val perpendicularDistance: Float = CartesianProduct()(normal, delta)
        if (perpendicularDistance == 0f) return LocalSurfaceNormal(ray.point, normal)

        val perpendicularDisplacement: CartesianVector = normal * perpendicularDistance
        val perpendicularProjection: Float = CartesianProduct()(perpendicularDisplacement, ray.direction)

        if (perpendicularProjection == 0f) return null

        return LocalSurfaceNormal(ray.point + ray.direction / perpendicularProjection, normal)
    }
}
