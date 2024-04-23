package fittibackendapp.dto

data class UserDetailDto(
    val id: Long,
    val age: Int?,
    val height: Double?,
    val weight: Double?,
    val muscleMass: Double?,
    val bodyFat: Double?,
    val targetWeight: Double?,
    val targetMuscleMass: Double?,
    val user: UserDto,
)
