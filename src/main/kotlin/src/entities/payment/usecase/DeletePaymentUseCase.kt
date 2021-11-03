package src.entities.payment.usecase

import src.entities.payment.model.Payment

interface DeletePaymentUseCase {

    fun deletePaymentById(paymentDomain: Payment)

}
