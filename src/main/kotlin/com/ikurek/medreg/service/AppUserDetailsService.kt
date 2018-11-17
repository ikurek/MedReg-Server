package com.ikurek.medreg.service

import com.ikurek.medreg.constants.SecurityConstants
import com.ikurek.medreg.exception.TokenNotAssignedToUserException
import com.ikurek.medreg.model.AppUserModel
import com.ikurek.medreg.repository.AppUserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(private val appUserRepository: AppUserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val applicationUser = appUserRepository.findByEmail(username)

        return if (applicationUser == null) {
            throw UsernameNotFoundException(username)
        } else {
            User(applicationUser.email, applicationUser.password, emptyList<GrantedAuthority>())
        }
    }

    fun loadUserByAuthToken(token: String): AppUserModel {
        val key = Keys.hmacShaKeyFor(SecurityConstants.SECRET.toByteArray())

        val userEmailFromToken = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .body
                .subject

        val user = appUserRepository.findByEmail(userEmailFromToken) ?: throw TokenNotAssignedToUserException()

        return user!!
    }

    fun loadUserByEmail(email: String): AppUserModel? {
        return appUserRepository.findByEmail(email)
    }

    fun saveOrUpdateUser(user: AppUserModel): AppUserModel {
        val savedUser = appUserRepository.save(user)
        return savedUser
    }
}