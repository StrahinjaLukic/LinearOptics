package src.main.kotlin.geometry

import src.main.kotlin.CartesianVector
import src.main.kotlin.minus

class Distance {
    companion object {
        fun vector(a: CartesianVector, b: CartesianVector): CartesianVector = b - a
        fun scalar(a: CartesianVector, b: CartesianVector): Float = VectorNorm.instance(vector(a, b))
    }
}