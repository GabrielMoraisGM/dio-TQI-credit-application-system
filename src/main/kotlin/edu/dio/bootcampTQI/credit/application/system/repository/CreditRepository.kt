package edu.dio.bootcampTQI.credit.application.system.repository

import edu.dio.bootcampTQI.credit.application.system.model.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CreditRepository : JpaRepository <Credit, Long>{
    @Query(value = "Select * from CREDIT WHERE customer_id_customer = ?1 and credit_code = ?2", nativeQuery = true)
    fun findByCreditCode(customerId: Long, creditCode: UUID, ) : Credit
    @Query(value = "Select * from CREDIT WHERE customer_id_customer = ?1", nativeQuery = true)
    fun findAllByCustomerId(customerId: Long): List<Credit>
}