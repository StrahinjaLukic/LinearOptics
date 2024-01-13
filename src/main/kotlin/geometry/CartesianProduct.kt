package src.main.kotlin.geometry

import src.main.kotlin.CartesianVector

class CartesianProduct : (CartesianVector, CartesianVector) -> Float {
    override operator fun invoke(a: CartesianVector, b: CartesianVector): Float = a.x * b.x + a.y * b.y + a.z * b.z

    companion object
    {
        val instance = CartesianProduct()
    }
}
