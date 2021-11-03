package src.entities.payment.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import src.entities.payment.model.Payment
import src.entities.payment.repository.PaymentRepository
import src.entities.payment.usecase.NewPaymentUseCase

@Service
class NewPaymentService(

    @Autowired
    private val repository: PaymentRepository

) : NewPaymentUseCase {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun createPayment(paymentDomain: Payment): Payment {
        log.info("Creating new Payment for the Worker: ${paymentDomain.worker.id}")

        return repository.save(paymentDomain)
    }


}