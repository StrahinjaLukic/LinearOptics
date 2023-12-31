package src.main.kotlin

class CartesianProduct : (CartesianVector, CartesianVector) -> Float {
    override operator fun invoke(a: CartesianVector, b: CartesianVector): Float = a.x * b.x + a.y * b.y + a.z * b.z
}
