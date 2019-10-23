package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.dto.IncomeResponseDTO
import com.coelhocaique.finance.api.mapper.IncomeMapper
import com.coelhocaique.finance.core.service.IncomeService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class IncomeHandler (private val incomeService: IncomeService) {

    fun create(req: ServerRequest) =
            req.bodyToMono(IncomeRequestDTO::class.java)
                    .flatMap { incomeService.create(IncomeMapper.toIncomeDTO(it)) }
                    .flatMap { IncomeMapper.toIncomeResponseDTO(it) }
                    .flatMap { generateResponse(it) }

    fun findById(req: ServerRequest) =
            Mono.just(req.pathVariable("id"))
                    .flatMap { incomeService.findById(it) }
                    .flatMap { IncomeMapper.toIncomeResponseDTO(it) }
                    .flatMap { generateResponse(it) }

    fun delete(req: ServerRequest) =
            Mono.just(req.pathVariable("id"))
                    .flatMap { incomeService.findById(it) }
                    .flatMap { IncomeMapper.toIncomeResponseDTO(it) }
                    .flatMap { generateResponse(it) }

    internal fun <T> generateResponse(body: T) =
        ServerResponse.ok().body(BodyInserters.fromObject(body))
                .onErrorResume(Throwable::class.java) {
                    ServerResponse.status(500).body(BodyInserters.fromObject(it))
                }




}