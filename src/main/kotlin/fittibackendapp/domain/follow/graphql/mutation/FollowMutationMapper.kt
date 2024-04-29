package fittibackendapp.domain.follow.graphql.mutation

import fittibackendapp.domain.follow.service.FollowService
import fittibackendapp.security.component.ArgumentResolver
import org.springframework.web.bind.annotation.RestController

@RestController
class FollowMutationMapper(
    private val followService: FollowService,
    private val argumentResolver: ArgumentResolver
) {
    // @MutationMapping
    // fun putFollow(
    //     @Argument
    //
    // )
}
