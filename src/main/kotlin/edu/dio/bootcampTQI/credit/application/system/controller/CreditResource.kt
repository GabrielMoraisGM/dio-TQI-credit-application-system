package edu.dio.bootcampTQI.credit.application.system.controller

import edu.dio.bootcampTQI.credit.application.system.dto.CreditDto
import edu.dio.bootcampTQI.credit.application.system.dto.CreditView
import edu.dio.bootcampTQI.credit.application.system.dto.CreditViewList
import edu.dio.bootcampTQI.credit.application.system.model.Credit
import edu.dio.bootcampTQI.credit.application.system.service.impl.CreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors

@RestController
class CreditResource(
    private val creditService: CreditService
){
    @PostMapping
    fun saveCredit(@RequestBody creditDto: CreditDto): ResponseEntity<String>{
        val credit: Credit = this.creditService.save(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body("[LOG] Credit ${credit.creditCode} - Customer ${credit.customer?.firstName} saved!")
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId")customerId: Long): ResponseEntity<List<CreditViewList>>{
        val creditViewList: List<CreditViewList> = this.creditService.findAllByCustumerId(customerId).stream().map {
            credit: Credit -> CreditViewList(credit)
        }.collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(creditViewList)
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(@RequestParam(value="customerId") customerId: Long,
                         @PathVariable creditCode: UUID) : ResponseEntity<CreditView> {
        val credit: Credit = this.creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(CreditView(credit))
    }
}