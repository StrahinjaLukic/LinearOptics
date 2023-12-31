package src.main.kotlin

// TODO: It would be nice to have an abstract base class vector and implement conversions between representations.
data class CartesianVector(val x: Float, val y: Float, val z: Float)

inline operator fun CartesianVector.times(r: Float): CartesianVector = CartesianVector(x * r, y * r, z * r)
inline operator fun CartesianVector.div(r: Float): CartesianVector = CartesianVector(x / r, y / r, z / r)
inline operator fun CartesianVector.minus(other: CartesianVector): CartesianVector =
    CartesianVector(x - other.x, y - other.y, z - other.z)
inline operator fun CartesianVector.plus(other: CartesianVector): CartesianVector =
    CartesianVector(x + other.x, y + other.y, z + other.z)
