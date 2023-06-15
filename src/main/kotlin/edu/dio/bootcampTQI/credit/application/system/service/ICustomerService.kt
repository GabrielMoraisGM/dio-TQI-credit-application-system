package edu.dio.bootcampTQI.credit.application.system.service

import edu.dio.bootcampTQI.credit.application.system.model.Customer

interface ICustomerService{

    fun save(customer: Customer): Customer
    fun findById(customerId: Long): Customer
    fun delete(customerId: Long)
}