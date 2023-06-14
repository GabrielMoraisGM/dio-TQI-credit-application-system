package edu.dio.bootcampTQI.credit.application.system.service

import edu.dio.bootcampTQI.credit.application.system.model.Customer

interface ICustomerService {

    fun save(customer: Customer): Customer
    fun findById(custumerId: Long): Customer
    fun delete(custumerId: Long)
}