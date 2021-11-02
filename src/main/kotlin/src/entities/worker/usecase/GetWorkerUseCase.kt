package src.entities.worker.usecase

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import src.entities.worker.model.Worker

interface GetWorkerUseCase {

    fun getWorkerById(workerId: Long): Worker

    fun getAllWorkers(
        active: Boolean,
        pageable: Pageable
    ): Page<Worker>

}
