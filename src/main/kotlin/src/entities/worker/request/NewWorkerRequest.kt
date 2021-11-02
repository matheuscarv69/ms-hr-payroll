package src.entities.worker.request

import io.swagger.annotations.ApiModelProperty
import src.entities.worker.model.Worker
import java.math.BigDecimal
import javax.validation.constraints.*

data class NewWorkerRequest(

    @ApiModelProperty(value = "User Id", position = 1, required = true)
    @field:NotNull
    @field:Positive
    @field:Min(1)
    val userId: Long,

    @ApiModelProperty(value = "Daily Income", position = 2, required = true)
    @field:NotNull
    @field:Positive
    val dailyIncome: BigDecimal
) {

    fun toModel() = Worker(
        id = this.userId,
        dailyIncome = dailyIncome
    )

}
