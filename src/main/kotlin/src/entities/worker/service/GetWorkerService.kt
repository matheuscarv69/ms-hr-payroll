package src.entities.worker.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import src.configs.exception.WorkerNotFoundException
import src.entities.worker.model.Worker
import src.entities.worker.repository.WorkerRepository
import src.entities.worker.usecase.GetWorkerUseCase

@Service
class GetWorkerService(

    @Autowired
    private val repository: WorkerRepository

) : GetWorkerUseCase {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun getWorkerById(workerId: Long): Worker {
        log.info("Getting Worker by ID: $workerId")

        return repository.findById(workerId).orElseThrow {
            WorkerNotFoundException("This Worker ID: $workerId not found.")
        }
    }

    override fun getAllWorkers(
        active: Boolean,
        pageable: Pageable
    ): Page<Worker> {
        log.info("Getting all Workers")

        return repository.findAllByActive(active, pageable)
    }


}