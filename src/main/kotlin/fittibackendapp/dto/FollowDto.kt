package fittibackendapp.dto

import java.io.Serializable

data class FollowDto(
    val followerId: Long,
    val followeeId: Long,
    val id: Long
): Serializable
