package com.coelhocaique.finance.api.helper

import com.coelhocaique.finance.api.helper.exception.ApiException
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.switchIfEmpty

object ResponseHandler {

    private const val DEFAULT_ERROR_MESSAGE = "Internal error, please try again."

    data class ErrorResponse(val errors: List<String>)

    fun <T> generateResponse(body: Mono<T>, status: Int = 200): Mono<ServerResponse> {
        return body.onErrorMap { it }
                .flatMap { success(it, status)}
                .onErrorResume(Throwable::class.java) {
                    when (it) {
                        is ApiException -> mapApiException(it)
                        else -> mapException(it)
                    }
                }
                .switchIfEmpty { ServerResponse.notFound().build() }
    }

    private fun mapException(it: Throwable): Mono<ServerResponse> {
        return ServerResponse
                .status(500)
                .body(fromObject(buildErrorResponse(it.message)))
    }

    private fun mapApiException(it: ApiException): Mono<ServerResponse> {
        return ServerResponse
                .status(it.type.status)
                .body(fromObject(buildErrorResponse(it.messages)))
    }

    private fun <T> success(it: T, status: Int): Mono<ServerResponse> {
        return ServerResponse
                .status(status)
                .body(fromObject(it))
    }

    private fun buildErrorResponse(message: String?) = ErrorResponse(listOf(message ?: DEFAULT_ERROR_MESSAGE))

    private fun buildErrorResponse(errors: List<String>) = ErrorResponse(errors)
}