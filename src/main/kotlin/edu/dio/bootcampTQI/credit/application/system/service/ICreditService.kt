package edu.dio.bootcampTQI.credit.application.system.service

import edu.dio.bootcampTQI.credit.application.system.model.Credit
import java.util.*

interface ICreditService {

    fun save(credit: Credit): Credit
    fun findAllByCustomerId(customerId: Long): List<Credit>
    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit
}