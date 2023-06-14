package edu.dio.bootcampTQI.credit.application.system.model

import jakarta.persistence.*

@Entity
@Table(name = "Client")
data class Customer(
    @Column(nullable = false)
    var firstName: String = "",
    @Column(nullable = false)
    var lastName: String = "",
    @Column(nullable = false, unique = true)
    val cpf: String = "",
    @Column(nullable = false, unique = true)
    var email: String = "",
    @Column(nullable = false)
    var password: String = "",
    @Column(nullable = false) @Embedded
    var addres: Address = Address(),
    @Column(nullable = false) @OneToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE, CascadeType.PERSIST), mappedBy = "customer")
    var credits: List<Credit> = mutableListOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) val idCustumer: Long? = null
)

