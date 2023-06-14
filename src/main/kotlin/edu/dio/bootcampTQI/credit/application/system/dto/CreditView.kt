package edu.dio.bootcampTQI.credit.application.system.dto

import edu.dio.bootcampTQI.credit.application.system.enummeration.Status
import edu.dio.bootcampTQI.credit.application.system.model.Credit
import java.math.BigDecimal
import java.util.*

data class CreditView(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallment: Int,
    val status: Status,
    val email: String?,
    val incomeCustomer: BigDecimal?
    ){

    constructor(credit: Credit): this(
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallment = credit.numberOfInstallments,
        status = credit.status,
        email = credit.customer?.email,
        incomeCustomer = credit.customer?.income
    )
}