import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import src.main.kotlin.*
import src.main.kotlin.geometry.CartesianProduct
import java.util.stream.Stream
import kotlin.math.sqrt
import kotlin.math.pow

class PlanarRefractorTest {
    @ParameterizedTest
    @MethodSource("rayProvider")
    fun `Snell's law`(ray: Ray) {
        val refractionIndex = 1.5f
        val planeDirection = CartesianVector(0f, 0f, -1f)
        val refractor =
            OpticalSurface.makePlanarRefractor(Plane(CartesianVector(0f, 0f, 0f), planeDirection), refractionIndex)

        val cosineBefore = CartesianProduct()(ray.direction, planeDirection)
        val sineBefore = sqrt(1f - cosineBefore.pow(2))
        val directionAfter = refractor(ray).direction
        val cosineAfter = CartesianProduct()(directionAfter, planeDirection)
        val sineAfter = sqrt(1f - cosineAfter.pow(2))
        assert(
            (refractionIndex - sineBefore / sineAfter) / refractionIndex < 1e-6
        ) { "Direction before is ${ray.direction}; Direction after is $directionAfter; Sine before is $sineBefore; Sine after is $sineAfter" }
    }

    companion object {
        @JvmStatic
        private fun rayProvider(): Stream<Ray> {
            val rayStart = CartesianVector(0f, 0f, -1f)
            val steps = listOf(-2f, -1f, 0f, 1f, 2f)
            val directions = steps.map { x: Float -> steps.map { CartesianVector(x, it, 1f) } }.flatten()
                .filter { it.x.pow(2) + it.y.pow(2) != 0f }
            val rays = directions.map { Ray(rayStart, it) }
            return rays.stream()
        }
    }
}
