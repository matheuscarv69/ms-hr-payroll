package src.entities.payment.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import src.entities.payment.repository.PaymentRepository
import src.entities.payment.usecase.GetPaymentUseCase

@Service
class GetPaymentService(

    @Autowired
    private val repository: PaymentRepository

) : GetPaymentUseCase {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun checkPaymentByWorker(workerId: Long, month: Int, year: Int): Boolean {
        log.info("Checking if Worker has already been paid: $workerId in month: $month at year: $year")

        return repository.getPaymentsByWorkerIdAndCreatedAt(workerId, month, year).isNotEmpty()
    }

}