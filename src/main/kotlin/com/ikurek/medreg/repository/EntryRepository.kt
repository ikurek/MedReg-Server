package com.ikurek.medreg.repository

import com.ikurek.medreg.model.AppUserModel
import com.ikurek.medreg.model.EntryModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EntryRepository : JpaRepository<EntryModel, Long> {

    fun findByAppUserModel(appUserModel: AppUserModel): List<EntryModel>

}