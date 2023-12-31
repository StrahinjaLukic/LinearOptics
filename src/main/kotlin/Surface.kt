package src.main.kotlin

interface Surface {
    fun intersection(ray: Ray): LocalSurfaceNormal?
}
