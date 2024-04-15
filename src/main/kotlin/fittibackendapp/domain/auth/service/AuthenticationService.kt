package fittibackendapp.domain.auth.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import fittibackendapp.common.exception.NotRegisteredEmailException
import fittibackendapp.common.exception.UnCorrectedPasswordException
import fittibackendapp.domain.auth.entity.Role
import fittibackendapp.domain.auth.entity.User
import fittibackendapp.domain.auth.repository.LoginTypeRepository
import fittibackendapp.domain.auth.repository.RoleRepository
import fittibackendapp.domain.auth.repository.UserRepository
import fittibackendapp.dto.UserDto
import fittibackendapp.dto.mapstruct.UserMapStruct
import fittibackendapp.exception.DuplicatedEmailException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Service
class AuthenticationService(
    private val tokenAlgorithm: Algorithm,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val loginTypeRepository: LoginTypeRepository,
    private val userMapStruct: UserMapStruct,
) {

    @Transactional
    fun register(
        email: String,
        password: String,
        name: String,
        loginType: String,
    ): UserDto {
        val role = roleRepository.findByName(Role.ROLE_USER) ?: throw RuntimeException("ROLE_USER가 없습니다.")
        val loginType =
            loginTypeRepository.findByName(loginType) ?: throw RuntimeException("EMAIL Login Type이  없습니다.")

        val existUser = userRepository.findByEmail(email)
        if (existUser != null) {
            throw DuplicatedEmailException()
        }

        val encodedPassword = if (password.isEmpty())
            passwordEncoder.encode(password)
        else password

        val user = User(
            email = email,
            password = encodedPassword,
            name = name,
            role = role,
            loginType = loginType,
        ).apply {
            userRepository.save(this)
        }

        return userMapStruct.toDto(user)
    }

    @Transactional
    fun login(
        email: String,
        password: String,
    ): String {
        val user = userRepository.findByEmail(email) ?: throw NotRegisteredEmailException()

        if (!passwordEncoder.matches(password, user.password)) {
            throw UnCorrectedPasswordException()
        }

        return JWT.create()
            .withClaim("email", email)
            .withClaim("userId", user.id)
            .withClaim("role", user.role.id)
            .withExpiresAt(
                Date.from(
                    LocalDateTime
                        .now()
                        .plusDays(1)
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toInstant(),
                ),
            )
            .sign(tokenAlgorithm)
    }
}
