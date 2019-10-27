package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.DebtRequestDTO
import com.coelhocaique.finance.api.handler.DebtParameterHandler.retrieveId
import com.coelhocaique.finance.api.handler.DebtParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.handler.FetchDebtCriteria.SearchType.*
import com.coelhocaique.finance.api.helper.ObjectMapper.toDebtDTO
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.core.service.DebtService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

@Component
class DebtHandler (private val service: DebtService) {

    fun create(req: ServerRequest): Mono<ServerResponse> =
            req.bodyToMono(DebtRequestDTO::class.java)
                    .flatMap { service.create(toDebtDTO(it)) }
                    .flatMap { generateResponse(it) }

    fun findById(req: ServerRequest): Mono<ServerResponse> =
            just(retrieveId(req))
                    .flatMap { service.findById(it) }
                    .flatMap { generateResponse(it) }

    fun fetchDebts(req: ServerRequest): Mono<ServerResponse> {
        val criteria = retrieveParameters(req)

        return when (criteria.searchType()) {
            REFERENCE_CODE -> findByReferenceCode(criteria.referenceCode!!)
            REFERENCE_DATE -> findByReferenceDate(criteria.referenceDate!!)
            RANGE_DATE -> findByRangeDate(criteria.dateFrom!!, criteria.dateTo!!)
            else -> error(IllegalArgumentException())
        }
    }

    private fun findByReferenceCode(referenceCode: String): Mono<ServerResponse> =
            service.findByReferenceCode(referenceCode)
                    .flatMap { generateResponse(it) }

    private fun findByReferenceDate(referenceDate: String): Mono<ServerResponse> =
            service.findByReferenceDate(referenceDate)
                    .flatMap { generateResponse(it) }

    private fun findByRangeDate(dateFrom: String, dateTo: String): Mono<ServerResponse> =
            service.findByReferenceDateBetween(dateFrom, dateTo)
                    .flatMap { generateResponse(it) }

}