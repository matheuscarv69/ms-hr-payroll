package src.entities.worker.repository

import org.springframework.data.jpa.repository.JpaRepository
import src.entities.worker.model.Worker

interface WorkerRepository : JpaRepository<Worker, Long>