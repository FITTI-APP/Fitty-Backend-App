package fittibackendapp.dto

import java.io.Serializable

data class TargetPcfRatioDto(
    val proteinRatio: Double,
    val carbohydrateRatio: Double,
    val fatRatio: Double,
    val id: Long
): Serializable
