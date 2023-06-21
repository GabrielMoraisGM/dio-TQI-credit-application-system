package edu.dio.bootcampTQI.credit.application.system.service.impl

import edu.dio.bootcampTQI.credit.application.system.exception.InternalErrorException
import edu.dio.bootcampTQI.credit.application.system.exception.NotFoundException
import edu.dio.bootcampTQI.credit.application.system.model.Credit
import edu.dio.bootcampTQI.credit.application.system.model.Customer
import edu.dio.bootcampTQI.credit.application.system.repository.CreditRepository
import edu.dio.bootcampTQI.credit.application.system.repository.CustomerRepository
import edu.dio.bootcampTQI.credit.application.system.service.ICreditService
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import org.springframework.web.bind.MethodArgumentNotValidException
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerRepository: CustomerRepository
): ICreditService{
    override fun save(credit: Credit): Credit {
        try {
            credit.apply {
                credit.customer = customerRepository.getReferenceById(credit.customer?.idCustomer!!)
            }
            return creditRepository.save(credit)
        }
        catch (ex: JpaObjectRetrievalFailureException) { throw ex} //if customer id is invalid
        catch (ex: MethodArgumentNotValidException) { throw ex } //if any argument is invalid
    }

    override fun findAllByCustomerId(customerId: Long): List<Credit>{
        val credit: List<Credit> = this.creditRepository.findAllByCustomerId(customerId)
        if (credit.isEmpty()) return credit
        else throw NotFoundException("No credits found with this customer ID:$customerId!")
    }


    override fun findByCreditCode(custumerId: Long, creditCode: UUID): Credit {
        try {
            val customer: Optional<Customer> = customerRepository.findById(custumerId)

            return (creditRepository.findByCreditCode(
                customer.get().idCustomer!!,
                creditCode
            ))
        }catch (ex: NoSuchElementException){ throw ex}
        catch (ex: InternalErrorException){ throw ex}
    }
}