package edu.dio.bootcampTQI.credit.application.system.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface Customer : JpaRepository <Customer, Long> {
}