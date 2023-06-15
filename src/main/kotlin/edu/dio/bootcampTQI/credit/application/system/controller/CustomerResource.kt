package edu.dio.bootcampTQI.credit.application.system.controller

import edu.dio.bootcampTQI.credit.application.system.dto.CustomerDto
import edu.dio.bootcampTQI.credit.application.system.dto.CustomerUpdateDto
import edu.dio.bootcampTQI.credit.application.system.dto.CustomerView
import edu.dio.bootcampTQI.credit.application.system.model.Customer
import edu.dio.bootcampTQI.credit.application.system.service.ICustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customer")
class CustomerResource(
    private val customerService: ICustomerService
){

    @PostMapping()
    fun saveCustomer(@Valid @RequestBody customerDto: CustomerDto): ResponseEntity<CustomerView>{
        val savedCustomer = this.customerService.save(customerDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerView(savedCustomer))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") customerId: Long): ResponseEntity<CustomerView>{
        val customer: Customer = this.customerService.findById(customerId)
    return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customer))
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable("id") customerId: Long) = this.customerService.delete(customerId)

    @PatchMapping
    fun updateCustomer(@RequestParam(value = "customerId")customerId: Long,
                       @Valid @RequestBody customerUpdateDto: CustomerUpdateDto
    ): ResponseEntity<CustomerView>{
        val customer: Customer = this.customerService.findById(customerId)
        val customerToUpdate = customerUpdateDto.toEntity(customer)
        val customerUpdated = this.customerService.save(customerToUpdate)
        return return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customerUpdated))
    }
}