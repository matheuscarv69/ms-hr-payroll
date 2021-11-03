package src.entities.payment.model

import com.fasterxml.jackson.annotation.JsonFormat
import src.entities.worker.model.Worker
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity(name = "Payment")
class Payment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:NotNull
    @field:Min(0)
    @Column(name = "daily_income")
    val dailyIncome: BigDecimal,

    @field:NotNull
    @field:Min(0)
    @Column(name = "worked_days")
    val workedDays: Int,

    @field:JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @field:NotNull
    @ManyToOne
    val worker: Worker
) {

    fun getTotal(): BigDecimal {
        val workedDays = BigDecimal(this.workedDays)

        return this.dailyIncome.multiply(workedDays).setScale(2)
    }

}