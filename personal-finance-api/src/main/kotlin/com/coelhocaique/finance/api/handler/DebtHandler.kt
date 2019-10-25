package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.DebtRequestDTO
import com.coelhocaique.finance.api.helper.ObjectMapper.toDebtDTO
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.core.service.DebtService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DebtHandler (private val service: DebtService) {

    fun create(req: ServerRequest): Mono<ServerResponse> =
            req.bodyToMono(DebtRequestDTO::class.java)
                .flatMap { service.create(toDebtDTO(it)) }
                .flatMap { generateResponse(it) }

}