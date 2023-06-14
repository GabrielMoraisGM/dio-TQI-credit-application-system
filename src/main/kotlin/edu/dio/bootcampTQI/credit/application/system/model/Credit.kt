package edu.dio.bootcampTQI.credit.application.system.model

import edu.dio.bootcampTQI.credit.application.system.enummeration.Status
import java.util.*
import java.math.BigDecimal
import java.time.LocalDate

data class Credit(
    val creditCode: UUID = UUID.randomUUID(),
    val creditValue: BigDecimal = BigDecimal.ZERO,
    val dayFirstInstallment: LocalDate,
    val numberOfInstallments: Int = 0,
    val status: Status = Status.IN_PROGRESS,
    val custumer: Custumer? = null,
    val idCredit: Long? = null
)
