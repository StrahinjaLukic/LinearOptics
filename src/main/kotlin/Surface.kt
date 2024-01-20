package src.main.kotlin

interface Surface {
    fun intersectionPoint(ray: Ray): CartesianVector?
    fun surfaceNormal(point: CartesianVector): CartesianVector

    fun intersection(ray: Ray): LocalSurfaceNormal? {
        val intersectionPoint = intersectionPoint(ray)
        return intersectionPoint?.let { LocalSurfaceNormal(it, surfaceNormal(intersectionPoint)) }
    }
}
