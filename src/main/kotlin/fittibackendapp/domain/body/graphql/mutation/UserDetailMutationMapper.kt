package fittibackendapp.domain.body.graphql.mutation

import fittibackendapp.domain.body.graphql.mutation.input.PutUserDetailInput
import fittibackendapp.domain.body.service.UserDetailService
import fittibackendapp.dto.UserDetailDto
import fittibackendapp.security.component.ArgumentResolver
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserDetailMutationMapper(
    private val userDetailService: UserDetailService,
    private val argumentResolver: ArgumentResolver
) {
    @MutationMapping
    fun putUserDetail(
        @Argument
        putUserDetailInput: PutUserDetailInput
    ): UserDetailDto {
        val userId = argumentResolver.getUserId()
        return userDetailService.putUserDetail(
            putUserDetailInput = putUserDetailInput,
            userId = userId,
        )
    }
}
