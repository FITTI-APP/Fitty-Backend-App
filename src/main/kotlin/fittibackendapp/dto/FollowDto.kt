package fittibackendapp.dto

import java.io.Serializable

data class FollowDto(
    val followerId: Long,
    val followingId: Long,
    val id: Long
): Serializable
