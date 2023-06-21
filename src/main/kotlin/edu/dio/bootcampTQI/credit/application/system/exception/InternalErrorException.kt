package edu.dio.bootcampTQI.credit.application.system.exception

data class InternalErrorException(override val message: String?): RuntimeException(message){

}