package edu.dio.bootcampTQI.credit.application.system.dto

import edu.dio.bootcampTQI.credit.application.system.model.Credit
import edu.dio.bootcampTQI.credit.application.system.model.Customer
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @field:NotNull(message = "Cannot be empty")
    val creditValue: BigDecimal,
    @field:Future
    val dayFirstOfInstallment: LocalDate,
    @field:NotNull(message = "Cannot be empty")
    val numberOfInstallment: Int,
    @field:NotNull(message = "Cannot be empty")
    val customerId: Long
){

    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallments = this.numberOfInstallment,
        customer = Customer(idCustomer = this.customerId)
    )
}