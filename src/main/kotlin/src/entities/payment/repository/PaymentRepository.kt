package src.entities.payment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import src.entities.payment.model.Payment

interface PaymentRepository : JpaRepository<Payment, Long> {

    @Query(
        value = "select * from hr_payroll.payment p where p.worker_id = :workerId and extract (month from p.created_at) = :month and extract(year from p.created_at) = :year",
        nativeQuery = true
    )
    fun getPaymentsByWorkerIdAndCreatedAt(
        @Param("workerId") workerId: Long,
        @Param("month") month: Int,
        @Param("year") year: Int
    ): List<Payment>
}
