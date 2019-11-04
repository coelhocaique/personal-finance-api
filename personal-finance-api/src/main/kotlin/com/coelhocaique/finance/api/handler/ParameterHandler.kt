package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.ParameterRequestDTO
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.REFERENCE_DATE
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrievePath
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForParameter
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForParameters
import com.coelhocaique.finance.api.helper.ObjectMapper
import com.coelhocaique.finance.api.helper.RequestValidator
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.business
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
        val response = just(retrievePath(req))
                .onErrorMap { it }
                .flatMap { service.findById(it.userId, it.id!!) }
                .flatMap { just(buildForParameter(req.uri().toString(), it)) }

        return generateResponse(response)
    }

    fun fetchParameters(req: ServerRequest): Mono<ServerResponse> {
        val response = just(retrieveParameters(req))
                .onErrorMap { it }
                .flatMap {
                    when (it.searchType()) {
                        REFERENCE_DATE -> findByReferenceDate(it)
                        else -> error(business("No parameters informed."))
                    }
                }
                .flatMap { buildForParameters(req.uri().toString(), it) }

        return generateResponse(response)
    }

    private fun findByReferenceDate(it: FetchCriteria) =
            service.findByReferenceDate(it.userId, it.referenceDate!!)
}