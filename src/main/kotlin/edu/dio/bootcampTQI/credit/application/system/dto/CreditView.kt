package edu.dio.bootcampTQI.credit.application.system.dto

import edu.dio.bootcampTQI.credit.application.system.enummeration.Status
import edu.dio.bootcampTQI.credit.application.system.model.Credit
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class CreditView(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallment: Int,
    val dayFirstInstallment: LocalDate,
    val status: Status,
    val idCustomer: Long,
    val name: String?,
    val email: String?,
    val incomeCustomer: BigDecimal?
    ){

    constructor(credit: Credit): this(
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallment = credit.numberOfInstallments,
        dayFirstInstallment = credit.dayFirstInstallment,
        status = credit.status,
        idCustomer = credit.customer?.idCustomer!!,
        name = credit.customer?.firstName,
        email = credit.customer?.email,
        incomeCustomer = credit.customer?.income
    )

}
