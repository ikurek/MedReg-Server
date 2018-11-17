package com.ikurek.medreg.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ikurek.medreg.constants.SecurityConstants.HEADER_STRING
import com.ikurek.medreg.exception.MalformedRequestBodyException
import com.ikurek.medreg.exception.UserAlreadyExistsException
import com.ikurek.medreg.model.AppUserModel
import com.ikurek.medreg.repository.AppUserRepository
import com.ikurek.medreg.service.AppUserDetailsService
import mu.KLogger
import mu.KotlinLogging
import org.apache.commons.logging.Log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AppUserController(
        var appUserDetailsService: AppUserDetailsService,
        var bCryptPasswordEncoder: BCryptPasswordEncoder,
        var objectMapper: ObjectMapper,
        var logger: KLogger
) {

    @PostMapping("/register")
    fun register(@RequestBody appUser: AppUserModel?): ResponseEntity<AppUserModel> {

        if (appUser == null || appUser.email.isBlank() || appUser.password.isBlank()) throw MalformedRequestBodyException()
        if (appUserDetailsService.loadUserByEmail(appUser.email) != null) throw UserAlreadyExistsException()

        // Encrypt user password
        val encryptedPassword = bCryptPasswordEncoder.encode(appUser.password)
        appUser.password = encryptedPassword

        // Save user
        val savedUser = appUserDetailsService.saveOrUpdateUser(appUser)
        logger.info("Registered user ${savedUser.id}: ${savedUser.email}")

        return ResponseEntity(savedUser, HttpStatus.CREATED)
    }

    @GetMapping("/validate")
    fun validateToken(@RequestHeader(HEADER_STRING) token: String): ResponseEntity<AppUserModel> {
        val userForToken = appUserDetailsService.loadUserByAuthToken(token)

        logger.info("Validated user ${userForToken.id}: ${userForToken.email}")
        return ResponseEntity(userForToken, HttpStatus.OK)
    }

}