package src.main.kotlin.geometry

import src.main.kotlin.CartesianVector
import kotlin.math.sqrt

class VectorNorm : (CartesianVector) -> Float {
    override operator fun invoke(x: CartesianVector): Float = sqrt(CartesianProduct.instance(x, x))

    companion object {
        val instance = VectorNorm()
        fun isNormed(vector: CartesianVector) = instance(vector) == 1f
    }
}
