package src.entities.payment.request

import io.swagger.annotations.ApiModelProperty
import src.entities.payment.model.Payment
import src.entities.worker.model.Worker
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class NewPaymentRequest(

    @ApiModelProperty(value = "Worked Days", position = 1, required = true)
    @field:NotNull
    @field:Positive
    @field:Min(1)
    val workedDays: Int

) {

    fun toModel(workerDomain: Worker) = Payment(
        dailyIncome = workerDomain.dailyIncome,
        workedDays = this.workedDays,
        worker = workerDomain
    )


}
