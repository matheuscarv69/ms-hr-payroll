package src.entities.payment.usecase

import src.entities.payment.model.Payment

interface GetPaymentUseCase {

    fun checkPaymentByWorker(workerId: Long, month: Int, year: Int): Boolean

    fun getPaymentById(paymentId: Long): Payment
}