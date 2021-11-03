package src.entities.payment.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import src.entities.payment.request.NewPaymentRequest
import src.entities.payment.usecase.NewPaymentUseCase
import src.entities.worker.usecase.GetWorkerUseCase
import javax.validation.Valid

@Api(tags = ["Payment"])
@RestController
@RequestMapping("/v1/payments")
class PaymentController(

    @Autowired
    private val getWorkerService: GetWorkerUseCase,

    @Autowired
    private val newPaymentService: NewPaymentUseCase

) {

    private val log = LoggerFactory.getLogger(this.javaClass)


    @ApiOperation("Make the Payment")
    @ApiResponses(
        value = [
            ApiResponse(code = 201, message = "Payment Made Successfully"),
            ApiResponse(code = 400, message = "Poorly Formatted Request"),
            ApiResponse(code = 500, message = "Internal Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/workers/{workerId}")
    fun createPayment(
        @PathVariable workerId: Long,
        @RequestBody @Valid request: NewPaymentRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Void> {
        log.info("Receiving request for make the payment for worker ID: $workerId")

        val workerDomain = getWorkerService.getWorkerById(workerId)

        val paymentDomain = newPaymentService.createPayment(request.toModel(workerDomain))

        val uri = uriBuilder
            .path("/v1/payments/{paymentId}")
            .buildAndExpand(paymentDomain.id)
            .toUri()

        return ResponseEntity.created(uri).build()
    }

}