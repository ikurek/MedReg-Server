package com.ikurek.medreg.exception

abstract class BaseException : RuntimeException() {
    override fun fillInStackTrace(): Throwable {
        return this
    }
}