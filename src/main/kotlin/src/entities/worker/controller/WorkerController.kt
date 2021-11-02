package src.entities.worker.controller

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
import src.entities.worker.request.NewWorkerRequest
import src.entities.worker.usecase.NewWorkerUseCase
import javax.validation.Valid

@Api(tags = ["Worker"])
@RestController
@RequestMapping("/v1/workers")
class WorkerController(

    @Autowired
    private val newWorkerService: NewWorkerUseCase

) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @ApiOperation("Register new Worker")
    @ApiResponses(
        value = [
            ApiResponse(code = 201, message = "Successfully registered Worker"),
            ApiResponse(code = 400, message = "Poorly Formatted Request"),
            ApiResponse(code = 500, message = "Internal Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createWorker(
        @RequestBody @Valid request: NewWorkerRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Void> {
        log.info("Receiving request for create worker: ${request.userId}")

        val worker = newWorkerService.createWorker(request.toModel())

        val uri = uriBuilder.path("/v1/workers/{workerId}").buildAndExpand(worker.id).toUri()

        return ResponseEntity.created(uri).build()
    }

}