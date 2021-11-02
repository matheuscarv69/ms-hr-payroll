package src.entities.worker.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import src.entities.worker.model.Worker
import src.entities.worker.repository.WorkerRepository
import src.entities.worker.usecase.NewWorkerUseCase
import javax.transaction.Transactional

@Service
class NewWorkerService(

    @Autowired
    private val repository: WorkerRepository

) : NewWorkerUseCase {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    override fun createWorker(workerDomain: Worker): Worker {
        log.info("Registering new Worker: ${workerDomain.id}")


        return repository.save(workerDomain)
    }


}