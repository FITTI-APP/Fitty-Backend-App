package fittibackendapp.domain.follow.repository

import fittibackendapp.domain.follow.entity.Follow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository: JpaRepository<Follow, Long> {
    fun findByFollowerIdAndFolloweeId(followerId: Long, followeeId: Long): Follow?
    fun findAllByFolloweeId(followeeId: Long): List<Follow>
    fun findAllByFollowerId(followerId: Long): List<Follow>
}
