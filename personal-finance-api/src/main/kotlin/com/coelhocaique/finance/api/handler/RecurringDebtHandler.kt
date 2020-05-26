package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.ObjectMapper.toRecurringDebtDTO
import com.coelhocaique.finance.api.dto.RecurringDebtRequestDTO
import com.coelhocaique.finance.api.handler.RequestParameterHandler.extractBody
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveAccountId
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrievePath
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForRecurringDebt
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForRecurringDebts
import com.coelhocaique.finance.api.helper.RequestValidator.validate
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.core.service.RecurringDebtService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

@Component
class RecurringDebtHandler (private val service: RecurringDebtService) {

    fun create(req: ServerRequest): Mono<ServerResponse> {
        return retrieveAccountId(req)
                .flatMap { extractBody<RecurringDebtRequestDTO>(req).map { itt -> itt.copy(accountId = it) } }
                .flatMap { validate(it) }
                .flatMap { service.create(toRecurringDebtDTO(it)) }
                .flatMap { just(buildForRecurringDebt(req.uri().toString(), it)) }
                .let { generateResponse(it,201)  }
    }

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        return retrievePath(req)
                .flatMap { service.findById(it.accountId, it.id!!) }
                .flatMap { just(buildForRecurringDebt(req.uri().toString(), it)) }
                .let { generateResponse(it) }
    }

    fun fetchAll(req: ServerRequest): Mono<ServerResponse> {
        return retrieveParameters(req)
                .flatMap { service.findAll(it.accountId) }
                .flatMap { buildForRecurringDebts(req.uri().toString(), it) }
                .let { generateResponse(it, onEmptyStatus = 204) }
    }

    fun deleteById(req: ServerRequest): Mono<ServerResponse> {
        return retrievePath(req)
                .flatMap { service.deleteById(it.accountId, it.id!!) }
                .let { generateResponse(it, 204) }
    }
}