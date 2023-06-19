package edu.dio.bootcampTQI.credit.application.system.exception

data class DeleteException (override val message: String?) : RuntimeException(message) {
}