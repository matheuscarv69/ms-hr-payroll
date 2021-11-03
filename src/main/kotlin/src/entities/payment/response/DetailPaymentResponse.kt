package src.entities.payment.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import src.entities.payment.model.Payment
import java.math.BigDecimal
import java.time.LocalDateTime

data class DetailPaymentResponse(

    @ApiModelProperty(value = "dailyIncome", position = 1)
    val dailyIncome: BigDecimal,

    @ApiModelProperty(value = "workedDays", position = 2)
    val workedDays: Int,

    @ApiModelProperty(value = "total", position = 3)
    val total: BigDecimal,

    @ApiModelProperty(value = "total", position = 4)
    @field:JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val createdAt: LocalDateTime,

    @ApiModelProperty(value = "workerId", position = 5)
    val workerId: Long

) {

    constructor(payment: Payment) : this(
        dailyIncome = payment.dailyIncome,
        workedDays = payment.workedDays,
        total = payment.getTotal(),
        createdAt = payment.createdAt,
        workerId = payment.worker.id!!
    )

}
