package src.entities.worker.usecase

import src.entities.worker.model.Worker

interface NewWorkerUseCase {

    fun createWorker(workerDomain: Worker) : Worker
}