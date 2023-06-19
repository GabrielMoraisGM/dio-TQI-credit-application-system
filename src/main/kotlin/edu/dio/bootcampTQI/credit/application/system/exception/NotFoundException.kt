package edu.dio.bootcampTQI.credit.application.system.exception

data class NotFoundException (override val message: String?): RuntimeException(message) {
}