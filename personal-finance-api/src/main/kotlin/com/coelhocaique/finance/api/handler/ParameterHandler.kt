package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.ParameterRequestDTO
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.RANGE_DATE
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.REFERENCE_DATE
import com.coelhocaique.finance.api.handler.RequestParameterHandler.extractBody
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrievePath
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveAccountId
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForParameter
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForParameters
import com.coelhocaique.finance.api.helper.Messages.NO_PARAMETERS
import com.coelhocaique.finance.api.dto.ObjectMapper.toParameterDTO
import com.coelhocaique.finance.api.helper.RequestValidator.validate
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.finance.core.service.ParameterService
import com.coelhocaique.finance.core.util.logger
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
        val response = retrieveAccountId(req)
                .flatMap { extractBody<ParameterRequestDTO>(req).map { itt -> itt.copy(accountId = it) } }
                .flatMap { validate(it) }
                .flatMap { service.create(toParameterDTO(it)) }
                .flatMap { just(buildForParameter(req.uri().toString(), it)) }

        return generateResponse(response, HttpStatus.CREATED.value())
    }

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val response = retrievePath(req)
                .flatMap { service.findById(it.accountId, it.id!!) }
                .flatMap { just(buildForParameter(req.uri().toString(), it)) }

        return generateResponse(response)
    }

    fun fetchParameters(req: ServerRequest): Mono<ServerResponse> {
        val response = retrieveParameters(req)
                .flatMap {
                    logger().info(it.toString())
                    when (it.searchType()) {
                        REFERENCE_DATE -> findByReferenceDate(it)
                        RANGE_DATE -> findByReferenceDateRange(it)
                        else -> error(business(NO_PARAMETERS))
                    }
                }
                .flatMap { buildForParameters(req.uri().toString(), it) }

        return generateResponse(response, onEmptyStatus = 204)
    }

    fun deleteById(req: ServerRequest): Mono<ServerResponse> {
        val response = retrievePath(req)
                .flatMap { service.deleteById(it.accountId, it.id!!) }

        return generateResponse(response, 204)
    }

    private fun findByReferenceDate(it: FetchCriteria) =
            service.findByReferenceDate(it.accountId, it.referenceDate!!)

    private fun findByReferenceDateRange(it: FetchCriteria) =
            service.findByReferenceDateRange(it.accountId, it.dateFrom!!, it.dateTo!!)


}