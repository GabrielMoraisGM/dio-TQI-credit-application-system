package edu.dio.bootcampTQI.credit.application.system.model

data class Custumer(
    var firstName: String,
    var lastName: String,
    val cpf: String,
    var email: String,
    var password: String,
    var addres: Address = Address(),
    var credits: List<Credit> = mutableListOf(),
    val idCustumer: Long? = null
)

