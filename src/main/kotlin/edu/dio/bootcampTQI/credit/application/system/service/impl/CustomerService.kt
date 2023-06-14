package edu.dio.bootcampTQI.credit.application.system.service.impl

import edu.dio.bootcampTQI.credit.application.system.model.Customer
import edu.dio.bootcampTQI.credit.application.system.repository.CustomerRepository
import edu.dio.bootcampTQI.credit.application.system.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class Customer(
    private val customerRepository: CustomerRepository
): ICustomerService {
    override fun save(customer: Customer): Customer{
        return customerRepository.save(customer)
    }
    override fun findById(custumerId: Long): Customer{
        return this.customerRepository.findById(custumerId).orElseThrow(){
            throw RuntimeException("[LOG] ID: $custumerId not found!")
        }
    }
    override fun delete(custumerId: Long){
        customerRepository.deleteById(custumerId)
    }
}