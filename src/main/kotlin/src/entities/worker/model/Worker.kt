package src.entities.worker.model

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity(name = "Worker")
class Worker(

    @Id
    @Column(nullable = false, unique = true)
    val id: Long?,

    @field:NotNull
    @field:Min(0)
    @Column(name = "daily_income", nullable = false)
    val dailyIncome: BigDecimal,

) {

    @field:NotNull
    @Column(nullable = false)
    var active: Boolean = true
        private set

    fun enableWorker(): Boolean {
        this.active = true
        return active
    }

    fun disableWorker(): Boolean {
        this.active = false
        return active
    }

}
