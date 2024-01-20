package src.main.kotlin.geometry

import src.main.kotlin.CartesianVector
import kotlin.math.pow
import kotlin.math.sqrt

class Angle {
    companion object {
        fun cosine(a: CartesianVector, b: CartesianVector) =
            CartesianProduct.instance(a, b) / VectorNorm.instance(a) / VectorNorm.instance(b)
        fun sine(a: CartesianVector, b: CartesianVector) = sqrt(1f - cosine(a, b).pow(2))
    }
}