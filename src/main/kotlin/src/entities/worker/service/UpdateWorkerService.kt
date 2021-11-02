package src.entities.worker.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import src.configs.exception.WorkerNotFoundException
import src.entities.worker.model.Worker
import src.entities.worker.repository.WorkerRepository
import src.entities.worker.usecase.UpdateWorkerUseCase
import javax.transaction.Transactional

@Service
class UpdateWorkerService(

    @Autowired
    private val repository: WorkerRepository

) : UpdateWorkerUseCase {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    override fun updateWorker(workerDomain: Worker) {
        log.info("Updating User: ${workerDomain.id}")

        repository.findById(workerDomain.id!!).orElseThrow {
            WorkerNotFoundException("This Worker ID: ${workerDomain.id} not found.")
        }.let { worker ->
            worker.dailyIncome = workerDomain.dailyIncome

            repository.save(worker)
            log.info("Worker Updated, id: ${worker.id}")
        }

    }

    @Transactional
    override fun enableWorker(workerId: Long) {
        log.info("Activating Worker: $workerId")

        repository.findById(workerId).orElseThrow {
            WorkerNotFoundException("This Worker ID: $workerId not found.")
        }.let { worker ->
            worker.enableWorker()
            repository.save(worker)
        }
    }

    @Transactional
    override fun disableWorker(workerId: Long) {
        log.info("Deactivating Worker: $workerId")

        repository.findById(workerId).orElseThrow {
            WorkerNotFoundException("This Worker ID: $workerId not found.")
        }.let { worker ->
            worker.disableWorker()
            repository.save(worker)
        }
    }

}