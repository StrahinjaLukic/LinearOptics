package src.main.kotlin

import kotlin.math.sqrt

class VectorNorm: (CartesianVector) -> Float {
    override operator fun invoke(x: CartesianVector): Float = sqrt(CartesianProduct()(x, x))
}
