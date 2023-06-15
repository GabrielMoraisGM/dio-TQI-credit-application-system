package edu.dio.bootcampTQI.credit.application.system.service.impl

import edu.dio.bootcampTQI.credit.application.system.exception.BusinessException
import edu.dio.bootcampTQI.credit.application.system.model.Credit
import edu.dio.bootcampTQI.credit.application.system.repository.CreditRepository
import edu.dio.bootcampTQI.credit.application.system.service.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
): ICreditService {
    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.idCustomer!!)
        }
        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomerId(customerId: Long): List<Credit>{
        val credit: List<Credit> = this.creditRepository.findAllByCustomerId(customerId)
        if (credit.isEmpty()) return credit
        else throw BusinessException("[LOG] no credits found with this customer ID:$customerId!")
    }

    override fun findByCreditCode(custumerId: Long ,creditCode: UUID): Credit {
        val credit: Credit = (this.creditRepository.findByCreditCode(creditCode)
            ?: throw BusinessException("[LOG] CreditCode $creditCode not found"))
        return if (credit.customer?.idCustomer == custumerId ) credit else throw IllegalArgumentException("[ALERT]Contact admin")
    }


}