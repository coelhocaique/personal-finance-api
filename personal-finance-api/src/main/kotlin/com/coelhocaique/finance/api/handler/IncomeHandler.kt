package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.RANGE_DATE
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.REFERENCE_DATE
import com.coelhocaique.finance.api.handler.ParameterHandler.retrieveId
import com.coelhocaique.finance.api.handler.ParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForIncome
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForIncomes
import com.coelhocaique.finance.api.helper.ObjectMapper
import com.coelhocaique.finance.api.helper.RequestValidator
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.finance.core.domain.dto.IncomeDTO
import com.coelhocaique.finance.core.service.IncomeService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

@Component
class IncomeHandler (private val service: IncomeService) {

    fun create(req: ServerRequest): Mono<ServerResponse> {
        val response = req.bodyToMono(IncomeRequestDTO::class.java)
                .flatMap { RequestValidator.validate(it) }
                .onErrorMap { it }
                .flatMap { service.create(ObjectMapper.toIncomeDTO(it)) }
                .flatMap { just(buildForIncome(req.uri().toString(), it)) }

        return generateResponse(response, HttpStatus.CREATED.value())
    }

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val response = just(retrieveId(req))
                .flatMap { service.findById(it) }
                .flatMap { just(buildForIncome(req.uri().toString(), it)) }
        return generateResponse(response)
    }

    fun fetchIncomes(req: ServerRequest): Mono<ServerResponse> {
        val response = just(retrieveParameters(req))
                .flatMap {
                    when (it.searchType()) {
                        REFERENCE_DATE -> findByReferenceDate(it.referenceDate!!)
                        RANGE_DATE -> findByReferenceDateRange(it.dateFrom!!, it.dateTo!!)
                        else -> error(business("No parameters informed."))
                    }
                }
                .flatMap { buildForIncomes(req.uri().toString(), it) }

        return generateResponse(response)
    }

    fun delete(req: ServerRequest): Mono<ServerResponse> {
        val response = just(retrieveId(req))
                .flatMap { service.findById(it) }

        return generateResponse(response)
    }

    private fun findByReferenceDate(referenceDate: String): Mono<List<IncomeDTO>> =
            service.findByReferenceDate(referenceDate)


    private fun findByReferenceDateRange(dateFrom: String, dateTo: String): Mono<List<IncomeDTO>> =
            service.findByReferenceDateRange(dateFrom, dateTo)
}