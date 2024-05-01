package fittibackendapp.domain.follow.graphql.query

import fittibackendapp.domain.follow.service.FollowService
import fittibackendapp.dto.UserDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FollowQueryMapper(
    private val followService: FollowService
) {
    @QueryMapping
    fun followersByUserId(
        @Argument
        userId: Long
    ): List<UserDto> {
        return followService.findFollowersByUserId(
            userId = userId,
        )
    }

    @QueryMapping
    fun followeesByUserId(
        @Argument
        userId: Long
    ): List<UserDto> {
        return followService.findFolloweesByUserId(
            userId = userId,
        )
    }
}
