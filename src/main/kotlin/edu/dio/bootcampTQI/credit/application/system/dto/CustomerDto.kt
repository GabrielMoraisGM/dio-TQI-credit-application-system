package edu.dio.bootcampTQI.credit.application.system.dto

import edu.dio.bootcampTQI.credit.application.system.model.Address
import edu.dio.bootcampTQI.credit.application.system.model.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
    @field:NotEmpty(message = "Cannot be empty")
    val firstName: String,
    @field:NotEmpty(message = "Cannot be empty")
    val lastName: String,
    @field:NotEmpty(message = "Cannot be empty")
    @field: CPF(message = "This invalid CPF")
    val cpf: String,
    @field: NotNull(message = "Cannot be empty")
    val income: BigDecimal,
    @field: NotEmpty(message = "Cannot be empty")
    @field: Email(message = "This invalid email")
    val email: String,
    @field: NotEmpty(message = "Cannot be empty")
    val password: String,
    @field: NotEmpty(message = "Cannot be empty")
    val zipCode: String,
    @field: NotEmpty(message = "Cannot be empty")
    val street: String

){

    fun toEntity(): Customer = Customer(
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        income = this.income,
        email = this.email,
        password = this.password,
        address = Address(
            zipCode = this.zipCode,
            street = this.street
        )
    )
}