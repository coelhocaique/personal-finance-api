package com.coelhocaique.finance.api.helper.exception

import java.lang.RuntimeException

data class ApiException(val type: ExceptionType,
                        val messages: List<String> = listOf()
): RuntimeException() {

    enum class ExceptionType(val status: Int){
        BUSINESS_EXCEPTION(400),
        UNAUTHORIZED_EXCEPTION(401),
        SERVER_EXCEPTION(500)
    }


    companion object ApiExceptionHelper {
        fun business(message: String) = ApiException(ExceptionType.BUSINESS_EXCEPTION, listOf(message))

        fun unauthorized(message: String) = ApiException(ExceptionType.UNAUTHORIZED_EXCEPTION, listOf(message))
    }
}