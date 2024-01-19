import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import src.main.kotlin.CartesianVector
import src.main.kotlin.Ray
import src.main.kotlin.SphericalSection
import src.main.kotlin.geometry.Distance
import java.util.stream.Stream

class SphericalSectionTest {
    @ParameterizedTest
    @MethodSource("originProvider")
    fun `Intersections of parallel rays in z`(rayOrigin: CartesianVector) {
        val rayDirection = CartesianVector(0f, 0f, 1f)

        val sphereCenter = CartesianVector(0f, 0f, 5f)
        val sphereRadius = 4f
        val sectionAxis = CartesianVector(0f, 0f, -1f)
        val sectionRadius = 3f
        val sphericalSection = SphericalSection(sphereCenter, sphereRadius, sectionAxis, sectionRadius)

        val rayIntersection = sphericalSection.intersection(Ray(rayOrigin, rayDirection))

        assertEquals(rayOrigin.x, rayIntersection?.point?.x)
        assertEquals(rayOrigin.y, rayIntersection?.point?.y)
        assertEquals(sphereRadius, Distance.scalar(sphereCenter, rayIntersection!!.point))
    }

    companion object {
        @JvmStatic
        private fun originProvider(): Stream<CartesianVector> {
            val steps = listOf(-2f, -1f, 0f, 1f, 2f)
            val origins = steps.map { x: Float -> steps.map { CartesianVector(x, it, 0f) } }.flatten()
            return origins.stream()
        }
    }
}
