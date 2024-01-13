package src.main.kotlin

import src.main.kotlin.geometry.*
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Class SphericalSection describes a section of a sphere such as would appear on one side of a plane if the plane was intersecting the sphere.
 *
 * @param centerPoint Geometrical center of the sphere.
 * @param radius Radius of the sphere.
 * @param sectionAxis Axial vector of the section.
 * @param sectionRadius Radius of the outer edge of the spherical section, in the direction perpendicular to the section axis.
 */
class SphericalSection(
    centerPoint: CartesianVector,
    private val radius: Float,
    sectionAxis: CartesianVector,
    private val sectionRadius: Float
) : Surface {
    private val sectionAxisRay = Ray(centerPoint, sectionAxis)
    private val centerPoint: CartesianVector
        get() = sectionAxisRay.point

    init {
        require(sectionRadius <= radius)
    }

    override fun surfaceNormal(pointOnSurface: CartesianVector): CartesianVector {
        val centerToSurfaceVector = Distance.vector(centerPoint, pointOnSurface)
        val centerToSurfaceDistance = VectorNorm.instance(centerToSurfaceVector)
        require((centerToSurfaceDistance - radius) / radius < 1e-6)
        return centerToSurfaceVector / centerToSurfaceDistance
    }

    override fun intersectionPoint(ray: Ray): CartesianVector? {
        val displacementToCenter = Distance.vector(ray.point, centerPoint)
        val displacementToCenterAlongRay = ray.direction * CartesianProduct()(ray.direction, displacementToCenter)
        val closestApproach = Distance.scalar(displacementToCenterAlongRay, displacementToCenter)

        if (closestApproach > radius) return null // Ray misses the sphere

        val distanceSurfaceToClosestAlongRay = sqrt(radius.pow(2) - closestApproach.pow(2))
        val approachCosine = CartesianProduct.instance(ray.direction, sectionAxisRay.direction)

        if (approachCosine < 0) // Ray angled inwards towards the center of the sphere
        {
            val rayToSurfaceVector = displacementToCenterAlongRay - ray.direction * distanceSurfaceToClosestAlongRay

            if (CartesianProduct.instance(ray.direction, rayToSurfaceVector) < 0) return null // Surface is behind ray.

            val intersectionPoint = ray.point + rayToSurfaceVector
            // Check if outside of section
            if (RayProjection.projectionDistance(intersectionPoint, sectionAxisRay) > sectionRadius) return null

            return intersectionPoint
        }

        // Ray parallel to the section plane or angled outwards from the center.

        if (approachCosine < sectionRadius / radius)
        { // May have two intersections with the spherical section. Try negative first. If ahead, it is the closer solution.
            val rayToSurfaceVector = displacementToCenterAlongRay - ray.direction * distanceSurfaceToClosestAlongRay

            if (CartesianProduct.instance(ray.direction, rayToSurfaceVector) >= 0) // Surface is ahead.
            {
                val intersectionPoint = ray.point + rayToSurfaceVector
                // Check if inside the section
                if (RayProjection.projectionDistance(intersectionPoint, sectionAxisRay) <= sectionRadius) return intersectionPoint
            }
        }

        // Try positive
        val rayToSurfaceVector = displacementToCenterAlongRay + ray.direction * distanceSurfaceToClosestAlongRay

        if (CartesianProduct.instance(ray.direction, rayToSurfaceVector) < 0) return null // Surface is behind ray.

        val intersectionPoint = ray.point + rayToSurfaceVector
        // Check if outside of section
        if (RayProjection.projectionDistance(intersectionPoint, sectionAxisRay) > sectionRadius) return null

        return intersectionPoint
    }
}