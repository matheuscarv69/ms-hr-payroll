package src.entities.payment.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import src.entities.payment.request.NewPaymentRequest
import src.entities.payment.response.DetailCheckingPaymentResponse
import src.entities.payment.response.DetailPaymentResponse
import src.entities.payment.usecase.DeletePaymentUseCase
import src.entities.payment.usecase.GetPaymentUseCase
import src.entities.payment.usecase.NewPaymentUseCase
import src.entities.worker.response.DetailWorkerResponse
import src.entities.worker.usecase.GetWorkerUseCase
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Api(tags = ["Payment"])
@Validated
@RestController
@RequestMapping("/v1/payments")
class PaymentController(

    @Autowired
    private val getWorkerService: GetWorkerUseCase,

    @Autowired
    private val newPaymentService: NewPaymentUseCase,

    @Autowired
    private val getPaymentService: GetPaymentUseCase,

    @Autowired
    private val deletePaymentService: DeletePaymentUseCase

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

    @ApiOperation("Check if Worker has Already been Paid")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Worker has Already been Paid"),
            ApiResponse(code = 400, message = "Poorly Formatted Request"),
            ApiResponse(code = 404, message = "Worker Not Found"),
            ApiResponse(code = 500, message = "Internal Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/workers/{workerId}")
    fun checkPaymentByWorker(
        @PathVariable workerId: Long,
        @RequestParam @Min(1) @Max(12) month: Int,
        @RequestParam @Min(2000) year: Int
    ): ResponseEntity<DetailCheckingPaymentResponse> {
        log.info("Receiving request for checking payment by worker id: $workerId")

        val workerIsPaid = getPaymentService.checkPaymentByWorker(workerId, month, year)

        return ResponseEntity.ok(
            DetailCheckingPaymentResponse(
                workerId = workerId,
                paid = workerIsPaid,
                month = month,
                year = year
            )
        )
    }

    @ApiOperation("Get Payment by ID")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Payment found successfully"),
            ApiResponse(code = 404, message = "Payment Not Found"),
            ApiResponse(code = 500, message = "Internal Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{paymentId}")
    fun getPaymentById(
        @PathVariable paymentId: Long,
    ): ResponseEntity<DetailPaymentResponse> {
        log.info("Receiving request for found Payment, id: $paymentId")

        val paymentResponse = DetailPaymentResponse(getPaymentService.getPaymentById(paymentId))

        return ResponseEntity.ok(paymentResponse)
    }

    @ApiOperation("Delete Payment")
    @ApiResponses(
        value = [
            ApiResponse(code = 204, message = "Payment successfully deleted"),
            ApiResponse(code = 404, message = "Payment Not Found"),
            ApiResponse(code = 500, message = "Internal Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{paymentId}")
    fun deletePaymentById(
        @PathVariable paymentId: Long,
    ): ResponseEntity<Void> {
        log.info("Receiving request for delete payment id: $paymentId")

        val paymentDomain = getPaymentService.getPaymentById(paymentId)

        deletePaymentService.deletePaymentById(paymentDomain)

        return ResponseEntity.noContent().build()
    }

}