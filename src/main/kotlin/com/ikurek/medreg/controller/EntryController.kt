package com.ikurek.medreg.controller

import com.ikurek.medreg.constants.SecurityConstants
import com.ikurek.medreg.model.EntryModel
import com.ikurek.medreg.service.AppUserDetailsService
import com.ikurek.medreg.service.EntryService
import mu.KLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class EntryController(
        var appUserDetailsService: AppUserDetailsService,
        var entryService: EntryService,
        var logger: KLogger
) {

    @GetMapping("/entries")
    fun getAllEntries(@RequestHeader(SecurityConstants.HEADER_STRING) token: String): ResponseEntity<List<EntryModel>> {
        val userForToken = appUserDetailsService.loadUserByAuthToken(token)
        val entries = entryService.loadAllByAppUser(userForToken)

        logger.info("Found ${entries.size} entries for user ${userForToken.id}: ${userForToken.email}")
        return ResponseEntity(entries, HttpStatus.OK)
    }

    @PostMapping("/entries")
    fun saveEntryForUser(@RequestHeader(SecurityConstants.HEADER_STRING) token: String, @RequestBody entryModel: EntryModel): ResponseEntity<EntryModel> {
        val userForToken = appUserDetailsService.loadUserByAuthToken(token)
        val savedEntry = entryService.saveForUser(entryModel, userForToken)

        logger.info("Saved entry ${savedEntry.id}: ${savedEntry.diseaseName} for user ${userForToken.id}: ${userForToken.email}")
        return ResponseEntity(savedEntry, HttpStatus.CREATED)
    }

    @GetMapping("/entries/{id}")
    fun getSingleEntry(@RequestHeader(SecurityConstants.HEADER_STRING) token: String, @PathVariable("id") id: Long): ResponseEntity<EntryModel> {
        val userForToken = appUserDetailsService.loadUserByAuthToken(token)
        val entry = entryService.loadSingleByIdForUser(id, userForToken)

        logger.info("Found entry ${entry.id}: ${entry.diseaseName} for user ${userForToken.id}: ${userForToken.email}")
        return ResponseEntity(entry, HttpStatus.OK)
    }

    @PatchMapping("/entries/{id}")
    fun updateEntry(@RequestHeader(SecurityConstants.HEADER_STRING) token: String,
                    @PathVariable("id") id: Long,
                    @RequestBody entryReceived: EntryModel): ResponseEntity<EntryModel> {
        val userForToken = appUserDetailsService.loadUserByAuthToken(token)

        entryReceived.id = id
        val entryStored = entryService.updateForUser(entryReceived, userForToken)

        logger.info("Updated entry ${entryStored.id}: ${entryStored.diseaseName} for user ${userForToken.id}: ${userForToken.email}")
        return ResponseEntity(entryStored, HttpStatus.OK)
    }

    @DeleteMapping("/entries/{id}")
    fun deleteEntry(@RequestHeader(SecurityConstants.HEADER_STRING) token: String,
                    @PathVariable("id") id: Long): ResponseEntity<String> {
        val userForToken = appUserDetailsService.loadUserByAuthToken(token)

        entryService.deleteByIdForUser(id, userForToken)

        logger.info("Deleted entry ${id} for user ${userForToken.id}: ${userForToken.email}")
        return ResponseEntity(HttpStatus.OK)
    }
}