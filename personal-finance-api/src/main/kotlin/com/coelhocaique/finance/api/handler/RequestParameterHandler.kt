package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.helper.Fields.AUTHORIZATION
import com.coelhocaique.finance.api.helper.Fields.DATE_FROM
import com.coelhocaique.finance.api.helper.Fields.DATE_TO
import com.coelhocaique.finance.api.helper.Fields.ID
import com.coelhocaique.finance.api.helper.Fields.PROPERTY_NAME
import com.coelhocaique.finance.api.helper.Fields.REF_CODE
import com.coelhocaique.finance.api.helper.Fields.REF_DATE
import com.coelhocaique.finance.api.helper.Messages.INVALID_REQUEST
import com.coelhocaique.finance.api.helper.Messages.MISSING_HEADERS
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.unauthorized
import com.coelhocaique.finance.core.util.logger
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

object RequestParameterHandler {

    fun retrieveParameters(req: ServerRequest): Mono<FetchCriteria> {
        return retrieveAccountId(req)
                    .map { FetchCriteria(accountId = it,
                             referenceCode = retrieveReferenceCode(req),
                             referenceDate = retrieveReferenceDate(req),
                             dateFrom = retrieveDateFrom(req),
                             dateTo = retrieveDateTo(req),
                             propertyName = retrievePropertyName(req)) }
    }

    fun retrievePath(req: ServerRequest): Mono<FetchCriteria> {
        val criteria =  retrieveAccountId(req)
                .map { FetchCriteria(accountId = it, id = retrieveId(req)) }
        logger().info(criteria.toString())
        return criteria
    }

    fun retrieveAccountId(req: ServerRequest): Mono<String> {
        val account = req.headers().header(AUTHORIZATION)
        return if (account.size > 0)
            just(account[0])
        else
            error { unauthorized(MISSING_HEADERS) }
    }

    inline fun <reified T> extractBody(req: ServerRequest): Mono<T> {
        return req.bodyToMono(T::class.java)
                .onErrorMap { business(INVALID_REQUEST, it) }
    }

    private fun retrieveId(req: ServerRequest): String {
        return req.pathVariable(ID)
    }

    private fun retrieveReferenceCode(req: ServerRequest): String? {
        return req.queryParam(REF_CODE).orElse(null)
    }

    private fun retrieveReferenceDate(req: ServerRequest): String? {
        return req.queryParam(REF_DATE).orElse(null)
    }

    private fun retrieveDateFrom(req: ServerRequest): String? {
        return req.queryParam(DATE_FROM).orElse(null)
    }

    private fun retrieveDateTo(req: ServerRequest): String? {
        return req.queryParam(DATE_TO).orElse(null)
    }

    private fun retrievePropertyName(req: ServerRequest): String? {
        return req.queryParam(PROPERTY_NAME).orElse(null)
    }
}