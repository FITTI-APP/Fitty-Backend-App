package fittibackendapp.domain.follow.graphql.mutation.input

data class FollowInput(
    val followerId: Long,
    val followingId: Long
)
