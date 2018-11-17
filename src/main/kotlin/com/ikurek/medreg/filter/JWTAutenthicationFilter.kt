package com.ikurek.medreg.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.ikurek.medreg.constants.SecurityConstants
import com.ikurek.medreg.constants.SecurityConstants.EXPIRATION_TIME
import com.ikurek.medreg.constants.SecurityConstants.HEADER_STRING
import com.ikurek.medreg.constants.SecurityConstants.SECRET
import com.ikurek.medreg.constants.SecurityConstants.SIGN_IN_URL
import com.ikurek.medreg.constants.SecurityConstants.TOKEN_PREFIX
import com.ikurek.medreg.exception.EmptyRequestException
import com.ikurek.medreg.model.AppUserModel
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAutenthicationFilter(var customAutenthicationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    init {
        this.setFilterProcessesUrl(SIGN_IN_URL)
    }


    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {

        if (request == null) throw EmptyRequestException()

        val appUser = ObjectMapper().readValue(request.inputStream, AppUserModel::class.java)
        val autenthication = UsernamePasswordAuthenticationToken(appUser.email, appUser.password, arrayListOf())

        return customAutenthicationManager.authenticate(autenthication)
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        // Data necessary for token generation
        val key = Keys.hmacShaKeyFor(SECRET.toByteArray())
        val user = (authResult!!.principal as User).username
        val expirationDate = Date(System.currentTimeMillis() + EXPIRATION_TIME)

        // Building new token
        val token = Jwts.builder()
                .setSubject(user)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact()

        // Adding token as header
        response!!.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
    }

}