package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.CustomAttributeRequestDTO
import com.coelhocaique.finance.api.dto.ObjectMapper.toCustomAttributeDTO
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.PROPERTY_NAME
import com.coelhocaique.finance.api.handler.RequestParameterHandler.extractBody
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveAccountId
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrievePath
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForCustomAttribute
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForCustomAttributes
import com.coelhocaique.finance.api.helper.RequestValidator.validate
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.core.service.CustomAttributeService
import com.coelhocaique.finance.core.util.logger
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

@Component
class CustomAttributeHandler (private val service: CustomAttributeService) {

    fun create(req: ServerRequest): Mono<ServerResponse> {
        val response = retrieveAccountId(req)
                .flatMap { extractBody<CustomAttributeRequestDTO>(req).map { itt -> itt.copy(accountId = it) } }
                .flatMap { validate(it) }
                .flatMap { service.create(toCustomAttributeDTO(it)) }
                .flatMap { just(buildForCustomAttribute(req.uri().toString(), it)) }

        return generateResponse(response, HttpStatus.CREATED.value())
    }

    fun fetchCustomAttributes(req: ServerRequest): Mono<ServerResponse> {
        val response = retrieveParameters(req)
                .onErrorMap { it }
                .flatMap {
                    logger().info(it.toString())
                    when (it.searchType()) {
                        PROPERTY_NAME -> findByPropertyName(it)
                        else -> findByAll(it)
                    }
                }
                .flatMap { buildForCustomAttributes(req.uri().toString(), it) }

        return generateResponse(response)
    }

    fun deleteById(req: ServerRequest): Mono<ServerResponse> {
        val response = retrievePath(req)
                .flatMap { service.deleteById(it.accountId, it.id!!) }

        return generateResponse(response, 204)
    }

    private fun findByPropertyName(it: FetchCriteria) =
            service.findByPropertyName(it.accountId, it.propertyName!!)

    private fun findByAll(it: FetchCriteria) = service.findAll(it.accountId)

}