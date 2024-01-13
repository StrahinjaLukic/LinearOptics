import org.junit.jupiter.api.Test
import src.main.kotlin.*
import src.main.kotlin.geometry.CartesianProduct
import kotlin.math.sqrt
import kotlin.math.pow

class PlanarRefractorTest {
    @Test
    fun `Snell's law`() {
        val refractionIndex = 1.5f
        val planeDirection = CartesianVector(0f, 0f, -1f)
        val refractor =
            OpticalSurface.makePlanarRefractor(Plane(CartesianVector(0f, 0f, 0f), planeDirection), refractionIndex)

        val rayStart = CartesianVector(0f, 0f, -1f)
        val steps = listOf(-2f, -1f, 0f, 1f, 2f)
        val directions = steps.map { x: Float -> steps.map { CartesianVector(x, it, 1f) } }.flatten()
        val rays = directions.map { Ray(rayStart, it) }.toMutableList()
            .filter { it.direction.x.pow(2) + it.direction.y.pow(2) != 0f }

        rays.forEach {
            val directionBefore = it.direction
            val cosineBefore = CartesianProduct()(directionBefore, planeDirection)
            val sineBefore = sqrt(1f - cosineBefore.pow(2))
            val directionAfter = refractor(it).direction
            val cosineAfter = CartesianProduct()(directionAfter, planeDirection)
            val sineAfter = sqrt(1f - cosineAfter.pow(2))
            assert(
                (refractionIndex -
                        sineBefore / sineAfter) / refractionIndex < 1e-6
            ) { "Direction before is $directionBefore; Direction after is $directionAfter; Sine before is $sineBefore; Sine after is $sineAfter" }
        }
    }
}