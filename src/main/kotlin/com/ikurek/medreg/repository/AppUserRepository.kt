package com.ikurek.medreg.repository

import com.ikurek.medreg.model.AppUserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository : JpaRepository<AppUserModel, Long> {

    fun findByEmail(email: String): AppUserModel?

}
