package fittibackendapp.domain.diet.repository

import fittibackendapp.domain.diet.entity.DietMealRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface DietMealRecordRepository: JpaRepository<DietMealRecord, Long> {
    @Query("SELECT e FROM DietMealRecord e WHERE e.user.id = :userId AND e.dateTime BETWEEN :fromDate AND :toDate")
    fun findDietMealRecordsBetweenDays(
        @Param("userId")
        userId: Long,
        @Param("fromDate")
        fromDate: LocalDateTime,
        @Param("toDate")
        toDate: LocalDateTime
    ): List<DietMealRecord>
}
