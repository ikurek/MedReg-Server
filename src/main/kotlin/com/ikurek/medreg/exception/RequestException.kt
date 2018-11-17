package com.ikurek.medreg.exception

import com.fasterxml.jackson.databind.ser.Serializers
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request cannot be empty")
class EmptyRequestException: BaseException()

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request body cannot be empty")
class EmptyRequestBodyException: BaseException()

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request body is malformed")
class MalformedRequestBodyException: BaseException()

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "No permission to access resource")
class UnauthorizedResourceAccess: BaseException()
