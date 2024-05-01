package fittibackendapp.domain.follow.service

import fittibackendapp.domain.auth.repository.UserRepository
import fittibackendapp.domain.follow.entity.Follow
import fittibackendapp.domain.follow.repository.FollowRepository
import fittibackendapp.dto.FollowDto
import fittibackendapp.dto.mapstruct.FollowMapStruct
import fittibackendapp.exception.AlreadyExistingFollowException
import fittibackendapp.exception.NotFoundFollowException
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
    fun createFollow(
        followerId: Long,
        followeeId: Long,
    ): FollowDto {
        val existingFollow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
        if (existingFollow != null) {
            throw AlreadyExistingFollowException()
        }

        val follower = userRepository.findByIdOrNull(followerId) ?: throw NotFoundUserException()
        val followee = userRepository.findByIdOrNull(followeeId) ?: throw NotFoundUserException()

        val follow = followRepository.save(
            Follow(
                follower = follower,
                followee = followee,
            ),
        )
        return followMapStruct.toDto(follow)
    }

    @Transactional
    fun deleteFollow(
        followerId: Long,
        followeeId: Long,
    ) {
        val follow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
            ?: throw NotFoundFollowException()

        followRepository.delete(follow)
    }
}
