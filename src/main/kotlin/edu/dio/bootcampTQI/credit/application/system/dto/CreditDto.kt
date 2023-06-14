package edu.dio.bootcampTQI.credit.application.system.dto

import edu.dio.bootcampTQI.credit.application.system.model.Credit
import edu.dio.bootcampTQI.credit.application.system.model.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    val creditValue: BigDecimal,
    val dayFirtOfInstallment: LocalDate,
    val numberOfInstallment: Int,
    val customerId: Long
){

    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirtOfInstallment,
        numberOfInstallments = this.numberOfInstallment,
        customer = Customer(idCustumer = this.customerId)
    )
}