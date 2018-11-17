package com.ikurek.medreg

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MedregApplication

fun main(args: Array<String>) {
    runApplication<MedregApplication>(*args)
}
