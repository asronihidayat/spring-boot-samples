package com.superboot.exception

import com.superboot.dto.ResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandler : ResponseEntityExceptionHandler
{
    constructor():super()

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handler(exception: ResourceNotFoundException, request: WebRequest): ResponseEntity<ResponseDto>
    {
        return ResponseEntity<ResponseDto>(ResponseDto(exception.message.toString()), HttpStatus.NOT_FOUND)
    }
}