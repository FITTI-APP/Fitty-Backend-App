package fittibackendapp.domain.diet.service

import fittibackendapp.domain.auth.repository.UserRepository
import fittibackendapp.domain.diet.entity.TargetPcfRatio
import fittibackendapp.domain.diet.repository.TargetPcfRatioRepository
import fittibackendapp.dto.TargetPcfRatioDto
import fittibackendapp.dto.mapstruct.TargetPcfRatioMapStruct
import fittibackendapp.exception.NotFoundUserException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TargetPcfRatioService(
    private val targetPcfRatioRepository: TargetPcfRatioRepository,
    private val targetPcfRatioMapStruct: TargetPcfRatioMapStruct,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun findOrSaveByUserId(userId: Long): TargetPcfRatioDto {
        val targetPcfRatio = targetPcfRatioRepository.findByUserId(userId)
            ?: run {
                val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundUserException()
                targetPcfRatioRepository.save(
                    TargetPcfRatio(
                        user = user,
                        proteinRatio = 0.3,
                        carbohydrateRatio = 0.4,
                        fatRatio = 0.3,
                    ),
                )
            }

        return targetPcfRatioMapStruct.toDto(targetPcfRatio)
    }
}
