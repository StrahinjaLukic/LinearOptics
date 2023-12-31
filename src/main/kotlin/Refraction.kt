package src.main.kotlin

import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

class Refraction : (CartesianVector, CartesianVector, Float) -> CartesianVector {
    override operator fun invoke(
        directionBefore: CartesianVector,
        surfaceNormal: CartesianVector,
        refractionIndex: Float
    ): CartesianVector {
        val vectorNorm = VectorNorm()

        val mutualCosine: Float =
            CartesianProduct()(surfaceNormal, directionBefore) / vectorNorm(surfaceNormal) / vectorNorm(directionBefore)
        val isInbound: Boolean = mutualCosine < 0
        val sineBefore = sqrt(1f - mutualCosine.pow(2))
        val sineAfter = if (isInbound) sineBefore / refractionIndex else sineBefore * refractionIndex
        val cosineSign = sign(mutualCosine)
        val cosineAfter = sqrt(1f - sineAfter.pow(2)) * cosineSign

        val perpendicular: CartesianVector = surfaceNormal * mutualCosine
        val tangential: CartesianVector = directionBefore - perpendicular

        return perpendicular * cosineAfter / mutualCosine + tangential * sineAfter / sineBefore
    }
}
