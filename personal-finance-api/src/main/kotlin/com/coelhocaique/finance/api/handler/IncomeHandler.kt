package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.mapper.IncomeMapper
import com.coelhocaique.finance.core.service.IncomeService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class IncomeHandler (private val incomeService: IncomeService) {

    val log = LoggerFactory.getLogger(IncomeHandler::class.java)

    fun create(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(IncomeRequestDTO::class.java)
                .flatMap { incomeService.create(IncomeMapper.toIncomeDTO(it)) }
                .flatMap { generateResponse(it) }
    }

    fun findById(req: ServerRequest) =
            Mono.just(req.pathVariable("id"))
                    .flatMap { incomeService.findById(it) }
                    .flatMap { generateResponse(it) }

    fun delete(req: ServerRequest) =
            Mono.just(req.pathVariable("id"))
                    .flatMap { incomeService.findById(it) }
                    .flatMap { generateResponse(it) }

    internal fun <T> generateResponse(body: T) =
        ServerResponse.ok().body(BodyInserters.fromObject(body))
                .onErrorResume(Throwable::class.java) {
                    log.error("fodeu", it)
                    ServerResponse.status(500).body(BodyInserters.fromObject(it))
                }
}