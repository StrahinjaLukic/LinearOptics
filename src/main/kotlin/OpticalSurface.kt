package src.main.kotlin

class OpticalSurface(
    private val surface: Surface,
    private val refractionIndex: Float
) : (Ray) -> Ray {
    override operator fun invoke(ray: Ray): Ray {
        // If the ray does not intersect the surface, it is not refracted.
        val intersection = surface.intersection(ray) ?: return ray
        return Ray(
            intersection.point,
            Refraction.instance(ray.direction, intersection.direction, refractionIndex)
        )
    }

    companion object {
        fun makePlanarRefractor(plane: Plane, refractionIndex: Float) = OpticalSurface(plane, refractionIndex)
        fun makeTransparentPlane(plane: Plane) = makePlanarRefractor(plane, 1f)
    }
}
