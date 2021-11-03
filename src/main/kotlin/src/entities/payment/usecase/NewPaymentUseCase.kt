package src.entities.payment.usecase

import src.entities.payment.model.Payment

interface NewPaymentUseCase {

    fun createPayment(paymentDomain: Payment): Payment

}