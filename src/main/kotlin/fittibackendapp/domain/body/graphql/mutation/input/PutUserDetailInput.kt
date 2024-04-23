package fittibackendapp.domain.body.graphql.mutation.input

import java.time.LocalDateTime

data class PutUserDetailInput(
    val id: Long?,
    val age: Int?,
    val height: Double?,
    val weight: Double?,
    val muscleMass: Double?,
    val bodyFat: Double?,
    val targetWeight: Double?,
    val targetMuscleMass: Double?,
    val recordTime: LocalDateTime,
)
