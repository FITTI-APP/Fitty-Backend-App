package fittibackendapp.common.dto

data class TokenDto(
    val email: String,
    val userId: Long,
    val role: Long,
)
