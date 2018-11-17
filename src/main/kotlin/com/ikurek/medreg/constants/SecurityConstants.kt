package com.ikurek.medreg.constants

object SecurityConstants {
    const val SECRET = "ImLazyPleaseDontStealThisKeyItIsDevelopmentOnlyAnyway"
    const val EXPIRATION_TIME: Long = 8640000000 //100 Days LOL
    const val TOKEN_PREFIX = "Token "
    const val HEADER_STRING = "Authorization"
    const val SIGN_UP_URL = "/api/register"
    const val SIGN_IN_URL = "/api/login"
}