package src.entities.payment.repository

import org.springframework.data.jpa.repository.JpaRepository
import src.entities.payment.model.Payment

interface PaymentRepository : JpaRepository<Payment, Long>
