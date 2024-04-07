package fittibackendapp.domain.body.repository

import fittibackendapp.domain.body.entity.UserDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDetailRepository: JpaRepository<UserDetail, Long>
