package edu.dio.bootcampTQI.credit.application.system.model

import jakarta.persistence.*

@Embeddable
data class Address(
    @Column(nullable = false)
    var zipCode: String = "",
    @Column(nullable = false)
    var street: String = ""
)
