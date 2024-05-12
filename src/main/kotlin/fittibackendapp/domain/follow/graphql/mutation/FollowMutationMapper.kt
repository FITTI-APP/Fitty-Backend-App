package fittibackendapp.domain.follow.graphql.mutation

import fittibackendapp.domain.follow.service.FollowService
import fittibackendapp.dto.FollowDto
import fittibackendapp.security.component.ArgumentResolver
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FollowMutationMapper(
    private val followService: FollowService,
    private val argumentResolver: ArgumentResolver
) {
    @MutationMapping
    fun followUser(
        @Argument
        followeeId: Long
    ): FollowDto {
        val userId = argumentResolver.getUserId()
        return followService.createFollow(
            followerId = userId,
            followeeId = followeeId,
        )
    }

    @MutationMapping
    fun unfollowUser(
        @Argument
        followeeId: Long
    ): Boolean {
        val userId = argumentResolver.getUserId()
        followService.deleteFollow(
            followerId = userId,
            followeeId = followeeId,
        )
        return true
    }
}
