package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.helper.ObjectMapper
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.core.service.IncomeService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class IncomeHandler (private val service: IncomeService) {

    fun create(req: ServerRequest): Mono<ServerResponse> =
            req.bodyToMono(IncomeRequestDTO::class.java)
                .flatMap { service.create(ObjectMapper.toIncomeDTO(it)) }
                .flatMap { generateResponse(it) }


    fun findById(req: ServerRequest): Mono<ServerResponse> =
            Mono.just(req.pathVariable("id"))
                    .flatMap { service.findById(it) }
                    .flatMap { generateResponse(it) }

    fun delete(req: ServerRequest): Mono<ServerResponse> =
            Mono.just(req.pathVariable("id"))
                    .flatMap { service.findById(it) }
                    .flatMap { generateResponse(it) }
}