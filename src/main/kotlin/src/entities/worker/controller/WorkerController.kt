package src.entities.worker.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import src.entities.worker.request.NewWorkerRequest
import src.entities.worker.request.UpdateWorkerRequest
import src.entities.worker.response.DetailWorkerResponse
import src.entities.worker.usecase.GetWorkerUseCase
import src.entities.worker.usecase.NewWorkerUseCase
import src.entities.worker.usecase.UpdateWorkerUseCase
import javax.validation.Valid

@Api(tags = ["Worker"])
@RestController
@RequestMapping("/v1/workers")
class WorkerController(

    @Autowired
    private val newWorkerService: NewWorkerUseCase,

    @Autowired
    private val updateWorkerService: UpdateWorkerUseCase,

    @Autowired
    private val getWorkerService: GetWorkerUseCase

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

    @ApiOperation("Update Worker")
    @ApiResponses(
        value = [
            ApiResponse(code = 204, message = "Worker Updated successfully"),
            ApiResponse(code = 400, message = "Poorly Formatted Request"),
            ApiResponse(code = 404, message = "Worker Not Found"),
            ApiResponse(code = 500, message = "Internal Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{workerId}")
    fun updateUser(
        @PathVariable workerId: Long,
        @RequestBody @Valid request: UpdateWorkerRequest,
    ): ResponseEntity<Void> {
        log.info("Receiving request for update worker: $workerId")

        updateWorkerService.updateWorker(request.toModel(workerId))

        return ResponseEntity.noContent().build()
    }

    @ApiOperation("Get Worker by ID")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Worker found successfully"),
            ApiResponse(code = 404, message = "Worker Not Found"),
            ApiResponse(code = 500, message = "Internal Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{workerId}")
    fun getWorkerById(
        @PathVariable workerId: Long,
    ): ResponseEntity<DetailWorkerResponse> {
        log.info("Receiving request for found worker, id: $workerId")

        val workerResponse = DetailWorkerResponse(getWorkerService.getWorkerById(workerId))

        return ResponseEntity.ok(workerResponse)
    }

    @ApiOperation("Get All Workers by Department")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Workers found successfully"),
            ApiResponse(code = 500, message = "Internal Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    fun getAllWorkersByDepartment(
        @RequestParam(required = false, defaultValue = true.toString()) active: Boolean,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = ["id"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable
    ): ResponseEntity<Page<DetailWorkerResponse>> {
        log.info("Receiving request for get all workers")

        val workersPage = getWorkerService.getAllWorkers(active, pageable)
            .map { worker ->
                DetailWorkerResponse(worker)
            }

        return ResponseEntity.ok(workersPage)
    }

    @ApiOperation("Enable Worker")
    @ApiResponses(
        value = [
            ApiResponse(code = 204, message = "Worker Enable successfully"),
            ApiResponse(code = 404, message = "Worker Not Found"),
            ApiResponse(code = 500, message = "Internal Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{workerId}/enable")
    fun enableWorker(
        @PathVariable workerId: Long,
    ): ResponseEntity<Void> {
        log.info("Receiving request for enable worker, ID: $workerId")

        updateWorkerService.enableWorker(workerId)

        return ResponseEntity.noContent().build()
    }

    @ApiOperation("Disable Worker")
    @ApiResponses(
        value = [
            ApiResponse(code = 204, message = "Worker Disable successfully"),
            ApiResponse(code = 404, message = "Worker Not Found"),
            ApiResponse(code = 500, message = "Internal Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{workerId}/disable")
    fun disableWorker(
        @PathVariable workerId: Long,
    ): ResponseEntity<Void> {
        log.info("Receiving request for disable worker, ID: $workerId")

        updateWorkerService.disableWorker(workerId)

        return ResponseEntity.noContent().build()
    }

}