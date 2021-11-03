package src.entities.payment.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import src.entities.payment.model.Payment
import src.entities.payment.repository.PaymentRepository
import src.entities.payment.usecase.DeletePaymentUseCase

@Service
class DeletePaymentService(

    @Autowired
    private val repository: PaymentRepository

) : DeletePaymentUseCase {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun deletePaymentById(paymentDomain: Payment) {
        log.info("Deleting Payment id: ${paymentDomain.id}")

        repository.delete(paymentDomain)
    }

}