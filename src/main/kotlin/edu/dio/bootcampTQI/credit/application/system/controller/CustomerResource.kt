package edu.dio.bootcampTQI.credit.application.system.controller

import edu.dio.bootcampTQI.credit.application.system.dto.CustomerDto
import edu.dio.bootcampTQI.credit.application.system.dto.CustomerUpdateDto
import edu.dio.bootcampTQI.credit.application.system.dto.CustomerView
import edu.dio.bootcampTQI.credit.application.system.model.Customer
import edu.dio.bootcampTQI.credit.application.system.service.ICustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
//@RestMapping("/api/customer")
class CustomerResource(
    private val customerService: ICustomerService
){

    @PostMapping("{customerDto}")
    fun saveCustomer(@RequestBody customerDto: CustomerDto): ResponseEntity<String>{
        val savedCustomer = this.customerService.save(customerDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer ${savedCustomer.email} saved!")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable customerId: Long): ResponseEntity<CustomerView>{
        val customer: Customer = this.customerService.findById(customerId)
    return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customer))
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable customerId: Long) = this.customerService.delete(customerId)

    @PatchMapping
    fun updateCustomer(@RequestParam(value = "customerId")customerId: Long,
                       @RequestBody customerUpdateDto: CustomerUpdateDto
    ): ResponseEntity<CustomerView>{
        val customer: Customer = this.customerService.findById(customerId)
        val customerToUpdate = customerUpdateDto.toEntity(customer)
        val customerUpdated = this.customerService.save(customerToUpdate)
        return return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customerUpdated))
    }
}