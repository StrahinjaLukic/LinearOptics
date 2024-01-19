import src.main.kotlin.*
import kotlin.math.abs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import src.main.kotlin.geometry.CartesianProduct
import src.main.kotlin.geometry.VectorNorm
import java.util.stream.Stream

class PlaneTest {
    @Test
    fun `Plane norm is normalized`() {
        val directionVector = CartesianVector(10f, 10f, 10f)
        val plane = Plane(CartesianVector(0f, 0f, 0f), directionVector)
        assert(abs(VectorNorm()(plane.normal) - 1) < 1e-6)
        assert(abs(CartesianProduct()(plane.normal, directionVector) / VectorNorm()(directionVector) - 1) < 1e-6)
    }

    @ParameterizedTest
    @MethodSource("directionProvider")
    fun `Simple ray intersection point`(rayDirection: CartesianVector) {
        val ez = CartesianVector(0f, 0f, 1f)
        val plane = Plane(CartesianVector(0f, 0f, 0f), ez)
        val ray = Ray(CartesianVector(0f, 0f, -1f), rayDirection)
        val planeIntersectionPoint = plane.intersectionPoint(ray)
        assertNotNull(planeIntersectionPoint)
        assertEquals(CartesianVector(rayDirection.x, rayDirection.y, 0f), planeIntersectionPoint)
    }

    companion object {
        @JvmStatic
        private fun directionProvider(): Stream<CartesianVector> {
            val steps = listOf(-2f, -1f, 0f, 1f, 2f)
            val origins = steps.map { x: Float -> steps.map { CartesianVector(x, it, 1f) } }.flatten()
            return origins.stream()
        }
    }
}
