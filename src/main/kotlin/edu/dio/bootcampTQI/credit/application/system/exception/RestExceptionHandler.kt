package edu.dio.bootcampTQI.credit.application.system.exception

import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerValidException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionDetails>{
        val erros: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.stream().forEach{
            erro: ObjectError ->
            val fieldName: String = (erro as FieldError).field
            val messageError: String? = erro.defaultMessage
            erros[fieldName] = messageError
        }
        return ResponseEntity(
            ExceptionDetails(
                title = "Unprocessable entity",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.UNPROCESSABLE_ENTITY.value(),
                exception = ex.javaClass.toString(),
                details = erros.toString()
            ), HttpStatus.UNPROCESSABLE_ENTITY
        )
    }

    @ExceptionHandler(DataAccessException::class)
    fun handlerValidException(ex: DataAccessException): ResponseEntity<ExceptionDetails>{

        return ResponseEntity(
            ExceptionDetails(
                title = "Conflict! Consult documentation",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.CONFLICT.value(),
                exception = ex.javaClass.toString(),
                details = "${ex.cause.toString()} \n ${ex.message}"
            ), HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(InternalErrorException::class)
    fun handlerValidException(ex: InternalErrorException): ResponseEntity<ExceptionDetails>{

        return ResponseEntity(
            ExceptionDetails(
                title = "Internal error",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception = ex.javaClass.toString(),
                details = "${ex.message}"
            ), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerValidException(ex: java.lang.IllegalArgumentException): ResponseEntity<ExceptionDetails>{

        return ResponseEntity(
            ExceptionDetails(
                title = "Bad Request! Consult documentation",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST.value(),
                exception = ex.javaClass.toString(),
                details = "${ex.message}"
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun handlerValidException(ex: Exception): ResponseEntity<ExceptionDetails>{

        return ResponseEntity(
            ExceptionDetails(
                title = "Client not found",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.NOT_FOUND.value(),
                exception = ex.javaClass.toString(),
                details = "${ex.message}"
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException::class)
    fun handlerValidException(ex: JpaObjectRetrievalFailureException): ResponseEntity<ExceptionDetails>{
        return ResponseEntity(
            ExceptionDetails(
                title = "Failed to locate entity",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST.value(),
                exception = ex.javaClass.toString(),
                details = "${ex.message}"
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handlerValidException(ex: NoSuchElementException): ResponseEntity<ExceptionDetails>{
        return ResponseEntity(
            ExceptionDetails(
                title = "Failed to locate customer",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST.value(),
                exception = ex.javaClass.toString(),
                details = "${ex.message}"
            ), HttpStatus.BAD_REQUEST
        )
    }
}