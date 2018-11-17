package com.ikurek.medreg.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User with this email already exists")
class UserAlreadyExistsException : BaseException()

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Token is not assigned to any user")
class TokenNotAssignedToUserException : BaseException()

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No data found")
class NoDataFoundException : BaseException()