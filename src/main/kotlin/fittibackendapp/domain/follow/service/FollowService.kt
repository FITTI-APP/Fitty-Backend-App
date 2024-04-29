package fittibackendapp.domain.follow.service

import fittibackendapp.domain.auth.repository.UserRepository
import fittibackendapp.domain.follow.entity.Follow
import fittibackendapp.domain.follow.repository.FollowRepository
import fittibackendapp.dto.FollowDto
import fittibackendapp.dto.mapstruct.FollowMapStruct
import fittibackendapp.exception.AlreadyExistingFollowException
import fittibackendapp.exception.NotFoundUserException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val followRepository: FollowRepository,
    private val followMapStruct: FollowMapStruct,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun putFollow(
        followerId: Long,
        followingId: Long,
    ): FollowDto {
        val existingFollow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
        if (existingFollow != null) {
            throw AlreadyExistingFollowException()
        }

        val follower = userRepository.findByIdOrNull(followerId) ?: throw NotFoundUserException()
        val following = userRepository.findByIdOrNull(followingId) ?: throw NotFoundUserException()

        val follow = followRepository.save(
            Follow(
                follower = follower,
                following = following,
            ),
        )

        return followMapStruct.toDto(follow)
    }
}
