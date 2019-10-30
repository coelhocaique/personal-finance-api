package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.DebtRequestDTO
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.*
import com.coelhocaique.finance.api.handler.ParameterHandler.retrieveId
import com.coelhocaique.finance.api.handler.ParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForDebt
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForDebts
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
                .onErrorMap { it }
                .flatMap { service.create(toDebtDTO(it)) }
                .flatMap { buildForDebts(req.uri().toString(), it) }

        return generateResponse(response,201)
    }

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val response = just(retrieveId(req))
                .flatMap { service.findById(it) }
                .flatMap { just(buildForDebt(req.uri().toString(), it)) }

        return generateResponse(response)
    }

    fun fetchDebts(req: ServerRequest): Mono<ServerResponse> {
        val response = just(retrieveParameters(req))
                .flatMap {
                    when (it.searchType()) {
                        REFERENCE_CODE -> findByReferenceCode(it.referenceCode!!)
                        REFERENCE_DATE -> findByReferenceDate(it.referenceDate!!)
                        RANGE_DATE -> findByRangeDate(it.dateFrom!!, it.dateTo!!)
                        else -> error(business("No parameters informed."))
                    }
                }
                .flatMap { buildForDebts(req.uri().toString(), it) }
        return generateResponse(response)
    }

    private fun findByReferenceCode(referenceCode: String): Mono<List<DebtDTO>> =
            service.findByReferenceCode(referenceCode)

    private fun findByReferenceDate(referenceDate: String): Mono<List<DebtDTO>> =
            service.findByReferenceDate(referenceDate)

    private fun findByRangeDate(dateFrom: String, dateTo: String): Mono<List<DebtDTO>> =
            service.findByReferenceDateBetween(dateFrom, dateTo)

}