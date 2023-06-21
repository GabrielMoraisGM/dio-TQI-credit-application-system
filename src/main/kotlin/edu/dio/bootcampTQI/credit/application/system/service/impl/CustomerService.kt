package edu.dio.bootcampTQI.credit.application.system.service.impl

import edu.dio.bootcampTQI.credit.application.system.exception.DeleteException
import edu.dio.bootcampTQI.credit.application.system.exception.NotFoundException
import edu.dio.bootcampTQI.credit.application.system.model.Customer
import edu.dio.bootcampTQI.credit.application.system.repository.CustomerRepository
import edu.dio.bootcampTQI.credit.application.system.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
): ICustomerService {
    override fun save(customer: Customer): Customer{
        return customerRepository.save(customer)
    }
    override fun findById(customerId: Long): Customer{
        return this.customerRepository.findById(customerId).orElseThrow(){
            throw NotFoundException("ID: $customerId not found!")
        }
    }
    override fun delete(customerId: Long){
        val customer: Customer = this.findById(customerId)
        try{
            customerRepository.delete(customer)
        }catch(ex: DeleteException){
            throw DeleteException("failed to exclude customer, please contact support") //maybe the connection with DB is down?
        }
    }

}