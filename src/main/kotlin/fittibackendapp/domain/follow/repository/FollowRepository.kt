package fittibackendapp.domain.follow.repository

import fittibackendapp.domain.follow.entity.Follow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository: JpaRepository<Follow, Long> {
    fun findByFollowerIdAndFollowingId(followerId: Long, followingId: Long): Follow?
}
