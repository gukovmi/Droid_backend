package ru.focus.start.loan.configuration

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleExceptionInternal(request: HttpServletRequest, ex: ResponseStatusException): ResponseEntity<Any> {

        return ResponseEntity(ex.message, ex.status)
    }
}