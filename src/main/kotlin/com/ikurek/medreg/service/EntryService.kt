package com.ikurek.medreg.service

import com.ikurek.medreg.exception.NoDataFoundException
import com.ikurek.medreg.exception.UnauthorizedResourceAccess
import com.ikurek.medreg.model.AppUserModel
import com.ikurek.medreg.model.EntryModel
import com.ikurek.medreg.repository.EntryRepository
import org.springframework.stereotype.Service

@Service
class EntryService(val entryRepository: EntryRepository) {

    fun loadAllByAppUser(appUserModel: AppUserModel): List<EntryModel> {

        val entries =  entryRepository.findByAppUserModel(appUserModel)

        if (entries.isEmpty()) throw NoDataFoundException()

        return entries
    }

    fun updateForUser(entryModel: EntryModel, userModel: AppUserModel): EntryModel {

        val storedEntry = entryRepository.findById(entryModel.id!!)

        if (storedEntry.isPresent) {
            if (storedEntry.get().appUserModel!!.id == userModel.id) {
                entryModel.appUserModel = userModel
                val savedEntry = entryRepository.save(entryModel)
                return savedEntry
            } else {
                throw UnauthorizedResourceAccess()
            }

        } else {
            throw NoDataFoundException()
        }
    }

    fun saveForUser(entryModel: EntryModel, userModel: AppUserModel): EntryModel {

        entryModel.appUserModel = userModel
        val savedEntry = entryRepository.save(entryModel)

        return savedEntry
    }

    fun loadSingleByIdForUser(id: Long, userModel: AppUserModel): EntryModel {
        val entry = entryRepository.findById(id)

        if (entry.isPresent) {

            if (entry.get().appUserModel!!.id != userModel.id) throw UnauthorizedResourceAccess()
            return entry.get()

        }
        else throw NoDataFoundException()
    }

    fun deleteByIdForUser(id: Long, userModel: AppUserModel) {
        val entry = entryRepository.findById(id)

        if (entry.isPresent) {
            if (entry.get().appUserModel!!.id == userModel.id) {
                entryRepository.deleteById(id)
            } else {
                throw UnauthorizedResourceAccess()
            }
        }
        else throw NoDataFoundException()
    }

}