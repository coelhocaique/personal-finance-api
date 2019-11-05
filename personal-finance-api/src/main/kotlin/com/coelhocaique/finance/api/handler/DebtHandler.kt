package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.DebtRequestDTO
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.*
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrievePath
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForDebt
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForDebts
import com.coelhocaique.finance.api.helper.Messages.NO_PARAMETERS
import com.coelhocaique.finance.api.helper.ObjectMapper.toDebtDTO
import com.coelhocaique.finance.api.helper.RequestValidator
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.finance.core.domain.dto.DebtDTO
import com.coelhocaique.finance.core.service.DebtService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

@Component
class DebtHandler (private val service: DebtService) {

    fun create(req: ServerRequest): Mono<ServerResponse> {
        val response = req.bodyToMono(DebtRequestDTO::class.java)
                .flatMap { RequestValidator.validate(it) }
                .flatMap { service.create(toDebtDTO(it)) }
                .flatMap { buildForDebts(req.uri().toString(), it) }

        return generateResponse(response,201)
    }

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val response = retrievePath(req)
                .flatMap { service.findById(it.userId, it.id!!) }
                .flatMap { just(buildForDebt(req.uri().toString(), it)) }

        return generateResponse(response)
    }

    fun fetchDebts(req: ServerRequest): Mono<ServerResponse> {
        val response = retrieveParameters(req)
                .flatMap {
                    when (it.searchType()) {
                        REFERENCE_CODE -> findByReferenceCode(it)
                        REFERENCE_DATE -> findByReferenceDate(it)
                        RANGE_DATE -> findByRangeDate(it)
                        else -> error(business(NO_PARAMETERS))
                    }
                }
                .flatMap { buildForDebts(req.uri().toString(), it) }

        return generateResponse(response)
    }

    private fun findByReferenceCode(it: FetchCriteria): Mono<List<DebtDTO>> =
            service.findByReferenceCode(it.userId, it.referenceCode!!)

    private fun findByReferenceDate(it: FetchCriteria): Mono<List<DebtDTO>> =
            service.findByReferenceDate(it.userId, it.referenceDate!!)

    private fun findByRangeDate(it: FetchCriteria): Mono<List<DebtDTO>> =
            service.findByReferenceDateBetween(it.userId, it.dateFrom!!, it.dateTo!!)

}