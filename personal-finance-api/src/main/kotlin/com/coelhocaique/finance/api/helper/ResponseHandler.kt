package com.coelhocaique.finance.api.helper

import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

object ResponseHandler {

//    val log = LoggerFactory.getLogger(IncomeHandler::class.java)


    fun <T> generateResponse(body: T): Mono<ServerResponse> {
        return ServerResponse.ok().body(BodyInserters.fromObject(body))
                .onErrorResume(Throwable::class.java) {
                    ServerResponse.status(500).body(BodyInserters.fromObject(it))
                }
    }
}