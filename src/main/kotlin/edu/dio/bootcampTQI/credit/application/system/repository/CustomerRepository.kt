package edu.dio.bootcampTQI.credit.application.system.repository

import edu.dio.bootcampTQI.credit.application.system.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository <Customer, Long> {
}