package edu.dio.bootcampTQI.credit.application.system.service.impl

import edu.dio.bootcampTQI.credit.application.system.model.Credit
import edu.dio.bootcampTQI.credit.application.system.repository.CreditRepository
import edu.dio.bootcampTQI.credit.application.system.service.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: Customer
): ICreditService {
    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.idCustumer!!)
        }
        return this.creditRepository.save(credit)
    }

    override fun findAllByCustumerId(custumerId: Long): List<Credit> = this.creditRepository.findAllByCustumerId(custumerId)

    override fun findByCreditCode(custumerId: Long ,creditCode: UUID): Credit {
        val credit: Credit = (this.creditRepository.findByCreditCode(creditCode)
            ?: throw RuntimeException("[LOG] CreditCode $creditCode not found"))
        return if (credit.customer?.idCustumer == custumerId ) credit else throw RuntimeException("[ALERT]Contact admin")
    }


}