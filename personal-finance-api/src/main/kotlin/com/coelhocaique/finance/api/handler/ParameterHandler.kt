package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.ParameterRequestDTO
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.RANGE_DATE
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.REFERENCE_DATE
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveId
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForIncome
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForIncomes
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForParameter
import com.coelhocaique.finance.api.helper.ObjectMapper
import com.coelhocaique.finance.api.helper.RequestValidator
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.finance.core.domain.dto.IncomeDTO
import com.coelhocaique.finance.core.service.ParameterService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

@Component
class ParameterHandler (private val service: ParameterService) {

    fun create(req: ServerRequest): Mono<ServerResponse> {
        val response = req.bodyToMono(ParameterRequestDTO::class.java)
                .flatMap { RequestValidator.validate(it) }
                .onErrorMap { it }
                .flatMap { service.create(ObjectMapper.toParameterDTO(it)) }
                .flatMap { just(buildForParameter(req.uri().toString(), it)) }

        return generateResponse(response, HttpStatus.CREATED.value())
    }

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val response = just(retrieveId(req))
                .flatMap { service.findById(it) }
                .flatMap { just(buildForParameter(req.uri().toString(), it)) }
        return generateResponse(response)
    }
}