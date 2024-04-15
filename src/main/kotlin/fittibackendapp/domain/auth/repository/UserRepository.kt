package fittibackendapp.domain.auth.repository

import fittibackendapp.domain.auth.entity.LoginType
import fittibackendapp.domain.auth.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun findByEmailAndLoginType(email: String, loginType: LoginType): User?

    fun findByEmail(email: String): User?
}
