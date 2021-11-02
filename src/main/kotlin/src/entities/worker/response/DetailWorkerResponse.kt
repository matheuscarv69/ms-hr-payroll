package src.entities.worker.response

import io.swagger.annotations.ApiModelProperty
import src.entities.worker.model.Worker
import java.math.BigDecimal

data class DetailWorkerResponse(

    @ApiModelProperty(value = "id", position = 1)
    val id: Long,

    @ApiModelProperty(value = "dailyIncome", position = 2)
    val dailyIncome: BigDecimal,

    @ApiModelProperty(value = "active", position = 3)
    val active: Boolean

) {

    constructor(worker: Worker) : this(
        id = worker.id!!,
        dailyIncome = worker.dailyIncome,
        active = worker.active
    )

}
