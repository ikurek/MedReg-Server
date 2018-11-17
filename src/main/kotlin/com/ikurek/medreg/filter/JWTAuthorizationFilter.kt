package com.ikurek.medreg.filter

import com.ikurek.medreg.constants.SecurityConstants.HEADER_STRING
import com.ikurek.medreg.constants.SecurityConstants.SECRET
import com.ikurek.medreg.constants.SecurityConstants.TOKEN_PREFIX
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.util.ArrayList
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(var customAuthenticationManager: AuthenticationManager) : BasicAuthenticationFilter(customAuthenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val header: String? = request.getHeader(HEADER_STRING)

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response)
        } else {

            val authentication = getAuthenthication(request)

            SecurityContextHolder.getContext().authentication = authentication
            chain.doFilter(request, response)
        }
    }

    private fun getAuthenthication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {

        val token = request.getHeader(HEADER_STRING)
        val key = Keys.hmacShaKeyFor(SECRET.toByteArray())

        // If token is provided, parse it and validate
        if (token.isNullOrBlank()) return null
        else {
            val user = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .body
                    .subject


            if (user != null) return UsernamePasswordAuthenticationToken(user, null, arrayListOf())
            else return null

        }
    }

}