package src.main.kotlin.geometry

import src.main.kotlin.CartesianVector
import kotlin.math.sqrt

class VectorNorm : (CartesianVector) -> Float {
    override operator fun invoke(x: CartesianVector): Float = sqrt(CartesianProduct()(x, x))

    companion object {
        val instance = VectorNorm()
    }
}