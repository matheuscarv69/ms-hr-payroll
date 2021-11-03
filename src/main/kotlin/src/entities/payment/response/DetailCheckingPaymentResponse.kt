package src.entities.payment.response

import io.swagger.annotations.ApiModelProperty

data class DetailCheckingPaymentResponse(

    @ApiModelProperty(value = "id", position = 1)
    val workerId: Long,

    @ApiModelProperty(value = "paid", position = 2)
    val paid: Boolean,

    @ApiModelProperty(value = "month", position = 3)
    val month: Int,

    @ApiModelProperty(value = "year", position = 4)
    val year: Int
)