package src.main.kotlin

import src.main.kotlin.geometry.Angle
import src.main.kotlin.geometry.CartesianProduct
import src.main.kotlin.geometry.VectorNorm
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

class Refraction : (CartesianVector, CartesianVector, Float) -> CartesianVector {
    override operator fun invoke(
        directionBefore: CartesianVector, surfaceNormal: CartesianVector, refractionIndex: Float
    ): CartesianVector {
        val mutualCosine: Float = Angle.cosine(surfaceNormal, directionBefore)
        val isInbound: Boolean = mutualCosine < 0
        val sineBefore = sqrt(1f - mutualCosine.pow(2))
        if (sineBefore == 0f)
        { // Incident ray is normal to the surface
            return directionBefore
        }
        val sineAfter = if (isInbound) sineBefore / refractionIndex else sineBefore * refractionIndex
        val cosineSign = sign(mutualCosine)
        val cosineAfter = sqrt(1f - sineAfter.pow(2)) * cosineSign

        val perpendicular: CartesianVector = surfaceNormal * mutualCosine
        val tangential: CartesianVector = directionBefore - perpendicular

        return perpendicular * cosineAfter / mutualCosine + tangential * sineAfter / sineBefore
    }

    companion object {
        val instance = Refraction()
    }
}
