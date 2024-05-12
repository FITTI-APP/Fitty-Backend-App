package fittibackendapp.dto

import java.io.Serializable

data class FollowDto(
    val follower: UserDto,
    val followee: UserDto,
    val id: Long
): Serializable
