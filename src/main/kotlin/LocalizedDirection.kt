package src.main.kotlin

import src.main.kotlin.geometry.VectorNorm

/**
 * Class LocalDirection describes a direction that is bound to a specific point in space.
 *
 * @param point The point in space at which the direction is specified.
 * @param directionVector The direction. Not necessarily normalized.
 */
data class LocalizedDirection(val point: CartesianVector, private var directionVector: CartesianVector) {

    val direction get() = directionVector

    init {
        directionVector /= VectorNorm()(directionVector)
    }
}

typealias Ray = LocalizedDirection
typealias LocalSurfaceNormal = LocalizedDirection
