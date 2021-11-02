package src.entities.worker.request

import io.swagger.annotations.ApiModelProperty
import src.entities.worker.model.Worker
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class UpdateWorkerRequest(

    @ApiModelProperty(value = "Daily Income", position = 1, required = true)
    @field:NotNull
    @field:Positive
    val dailyIncome: BigDecimal

) {

    fun toModel(workerId: Long) = Worker(
        id = workerId,
        dailyIncome = this.dailyIncome
    )

}
