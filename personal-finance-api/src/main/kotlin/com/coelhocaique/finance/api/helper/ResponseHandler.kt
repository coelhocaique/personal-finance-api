package com.coelhocaique.finance.api.helper

import com.coelhocaique.finance.api.helper.Messages.DEFAULT_ERROR_MESSAGE
import com.coelhocaique.finance.api.helper.exception.ApiException
import com.coelhocaique.finance.core.util.logger
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.switchIfEmpty

object ResponseHandler {

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
        logger().error("error=".plus(it.message), it)
        return ServerResponse
                .status(500)
                .body(fromObject(buildErrorResponse(it.message)))
    }

    private fun mapApiException(it: ApiException): Mono<ServerResponse> {
        logger().error("error=".plus(it.type.toString())
                .plus(", cause=")
                .plus(it.messages.joinToString { it }) , it)
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