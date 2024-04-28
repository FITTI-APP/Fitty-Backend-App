package fittibackendapp.common.configuration

import fittibackendapp.domain.auth.entity.LoginType
import fittibackendapp.domain.auth.entity.Role
import fittibackendapp.domain.auth.repository.LoginTypeRepository
import fittibackendapp.domain.auth.repository.RoleRepository
import fittibackendapp.domain.exercise.entity.Exercise
import fittibackendapp.domain.exercise.entity.ExerciseKind
import fittibackendapp.domain.exercise.entity.ExerciseSaveType
import fittibackendapp.domain.exercise.repository.ExerciseKindRepository
import fittibackendapp.domain.exercise.repository.ExerciseRepository
import fittibackendapp.domain.exercise.repository.ExerciseSaveTypeRepository
import org.springframework.boot.context.event.ApplicationStartingEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Configuration
class DataInitConfiguration(
    private val roleRepository: RoleRepository,
    private val exerciseKindRepository: ExerciseKindRepository,
    private val exerciseSaveTypeRepository: ExerciseSaveTypeRepository,
    private val exerciseRepository: ExerciseRepository,
    private val loginTypeRepository: LoginTypeRepository,
) {
    @Bean
    @Transactional
    @TransactionalEventListener(ApplicationStartingEvent::class)
    @EventListener(ApplicationStartingEvent::class)
    fun initEnumData() {
        if (roleRepository.findByName(Role.ROLE_USER) == null)
            roleRepository.save(Role(name = Role.ROLE_USER))
        if (roleRepository.findByName(Role.ROLE_ADMIN) == null)
            roleRepository.save(Role(name = Role.ROLE_ADMIN))

        if (loginTypeRepository.findByName("GOOGLE") == null)
            loginTypeRepository.save(LoginType(name = LoginType.GOOGLE))
        if (loginTypeRepository.findByName("KAKAO") == null)
            loginTypeRepository.save(LoginType(name = LoginType.KAKAO))
        if (loginTypeRepository.findByName("NAVER") == null)
            loginTypeRepository.save(LoginType(name = LoginType.NAVER))
        if (loginTypeRepository.findByName("FACEBOOK") == null)
            loginTypeRepository.save(LoginType(name = LoginType.FACEBOOK))
        if (loginTypeRepository.findByName("EMAIL") == null)
            loginTypeRepository.save(LoginType(name = LoginType.EMAIL))
    }

    @Bean
    @Transactional
    @TransactionalEventListener(ApplicationStartingEvent::class)
    @EventListener(ApplicationStartingEvent::class)
    fun initExerciseSaveType() {
        if (exerciseSaveTypeRepository.findByName("확정") == null) {
            exerciseSaveTypeRepository.save(
                ExerciseSaveType(
                    name = "확정",
                ),
            )
        }
        if (exerciseSaveTypeRepository.findByName("미확정") == null) {
            exerciseSaveTypeRepository.save(
                ExerciseSaveType(
                    name = "미확정",
                ),
            )
        }
    }

    @Bean
    @Transactional
    @TransactionalEventListener(ApplicationStartingEvent::class)
    @EventListener(ApplicationStartingEvent::class)
    fun initExerciseKindData() {
        val exercises = listOf(
            "하체",
            "복근",
            "가슴",
            "어깨",
            "등",
            "팔",
        )
        val exerciseKinds = exerciseKindRepository.findAll()

        val shouldDeleteExerciseKinds = exerciseKinds.filter { exerciseKind ->
            !exercises.contains(exerciseKind.name)
        }
        exerciseKindRepository.deleteAll(shouldDeleteExerciseKinds)

        val shouldAddExercises = exercises.filter { exercise ->
            exerciseKinds.none { exerciseKind ->
                exerciseKind.name == exercise
            }
        }
        exerciseKindRepository.saveAll(
            shouldAddExercises.map { exercise ->
                ExerciseKind(
                    name = exercise,
                )
            },
        )
    }

    @Bean
    @Transactional
    @TransactionalEventListener(ApplicationStartingEvent::class)
    @EventListener(ApplicationStartingEvent::class)
    fun initBasicExerciseData() {
        val allExercises = exerciseRepository.findAll()
        val lowerBodyExercises = listOf(
            "바벨 백 스쿼트",
            "바벨 프론트 스쿼트",
            "컨벤셔널 데드리프트",
            "레그 프레스 머신",
            "레그 익스텐션 머신",
            "레그 컬 머신",
            "스탠딩 카프 레이즈",
            "시티드 카프 레이즈",
            "덤벨 런지",
            "바벨 런지",
            "스모 데드리프트",
            "힙 쓰러스트 머신",
            "힙 어브덕션 머신",
            "힙 어덕션 머신",
            "바벨 힙 쓰러스트",
            "덤벨 스플릿 스쿼트",
            "힙 쓰러스트",
            "브이 스쿼트 머신",
            "리버스 브이 스쿼트 머신",
            "스미스 머신 스쿼트",
            "핵 스쿼트 머신",
        )
        val abdominalExercises = listOf(
            "레그 레이즈",
            "크런치",
            "플랭크",
            "싯업",
            "행잉 레그 레이즈",
            "사이드 크런치",
        )
        val chestExercises = listOf(
            "바벨 벤치 프레스",
            "인클라인 바벨 벤치 프레스",
            "디클라인 바벨 벤치 프레스",
            "덤벨 벤치 프레스",
            "덤벨 플라이",
            "팩 덱 플라이 머신",
            "인클라인 덤벨 벤치 프레스",
            "디클라인 덤벨 벤치 프레스",
            "케이블 크로스오버",
            "푸쉬업",
            "딥스",
            "덤벨 풀오버",
            "체스트 프레스 머신",
            "체스트 플라이 머신",
            "체스트 딥스 머신",
            "체스트 인클라인 머신",
            "체스트 디클라인 머신",
            "체스트 풀오버 머신",
        )
        val shoulderExercises = listOf(
            "덤벨 숄더 프레스",
            "오버헤드 프레스",
            "덤벨 사이드 레터럴 레이즈",
            "덤벨 프론트 레터럴 레이즈",
            "덤벨 벤트 오버 레터럴 레이즈",
            "페이스 풀",
            "사이드 레터럴 레이즈 머신",
            "바벨 업라이트 로우",
            "비하인드 넥 프레스",
            "이지바 업라이트 로우",
        )
        val backExercises = listOf(
            "풀업",
            "랫 풀다운",
            "케이블 시티드 로우",
            "바벨 로우",
            "덤벨 로우",
            "티바 로우",
            "원 암 덤벨 로우",
            "케이블 로우",
            "시티드 로우 머신",
            "친업",
            "루마니안 데드리프트",
        )
        val armExercises = listOf(
            "바벨 컬",
            "덤벨 컬",
            "해머 컬",
            "케이블 컬",
            "덤벨 트라이셉스 익스텐션",
            "케이블 트라이셉스 익스텐션",
            "케이블 푸쉬 다운",
            "덤벨 킥백",
        )

        val lowerBodyExerciseKind = exerciseKindRepository.findByName("하체")!!
        val abdominalExerciseKind = exerciseKindRepository.findByName("복근")!!
        val chestExerciseKind = exerciseKindRepository.findByName("가슴")!!
        val shoulderExerciseKind = exerciseKindRepository.findByName("어깨")!!
        val backExerciseKind = exerciseKindRepository.findByName("등")!!
        val armExerciseKind = exerciseKindRepository.findByName("팔")!!

        val shouldDeleteExercises = allExercises.filter { exercise ->
            !lowerBodyExercises.contains(exercise.name) &&
                !abdominalExercises.contains(exercise.name) &&
                !chestExercises.contains(exercise.name) &&
                !shoulderExercises.contains(exercise.name) &&
                !backExercises.contains(exercise.name) &&
                !armExercises.contains(exercise.name)
        }
        exerciseRepository.deleteAll(shouldDeleteExercises)

        val shouldAddLowerBodyExercises = lowerBodyExercises.filter { exercise ->
            allExercises.none { it.name == exercise }
        }
        exerciseRepository.saveAll(
            shouldAddLowerBodyExercises.map { exercise ->
                Exercise(
                    name = exercise,
                    exerciseKind = lowerBodyExerciseKind,
                )
            },
        )

        val shouldAddAbdominalExercises = abdominalExercises.filter { exercise ->
            allExercises.none { it.name == exercise }
        }
        exerciseRepository.saveAll(
            shouldAddAbdominalExercises.map { exercise ->
                Exercise(
                    name = exercise,
                    exerciseKind = abdominalExerciseKind,
                )
            },
        )

        val shouldAddChestExercises = chestExercises.filter { exercise ->
            allExercises.none { it.name == exercise }
        }
        exerciseRepository.saveAll(
            shouldAddChestExercises.map { exercise ->
                Exercise(
                    name = exercise,
                    exerciseKind = chestExerciseKind,
                )
            },
        )

        val shouldAddShoulderExercises = shoulderExercises.filter { exercise ->
            allExercises.none { it.name == exercise }
        }
        exerciseRepository.saveAll(
            shouldAddShoulderExercises.map { exercise ->
                Exercise(
                    name = exercise,
                    exerciseKind = shoulderExerciseKind,
                )
            },
        )

        val shouldAddBackExercises = backExercises.filter { exercise ->
            allExercises.none { it.name == exercise }
        }
        exerciseRepository.saveAll(
            shouldAddBackExercises.map { exercise ->
                Exercise(
                    name = exercise,
                    exerciseKind = backExerciseKind,
                )
            },
        )

        val shouldAddArmExercises = armExercises.filter { exercise ->
            allExercises.none { it.name == exercise }
        }
        exerciseRepository.saveAll(
            shouldAddArmExercises.map { exercise ->
                Exercise(
                    name = exercise,
                    exerciseKind = armExerciseKind,
                )
            },
        )
    }
}
