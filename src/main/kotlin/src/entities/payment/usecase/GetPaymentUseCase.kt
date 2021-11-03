package src.entities.payment.usecase

interface GetPaymentUseCase {

    fun checkPaymentByWorker(workerId: Long, month: Int, year: Int): Boolean

}