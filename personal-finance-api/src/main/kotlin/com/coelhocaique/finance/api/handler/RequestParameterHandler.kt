package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.helper.Fields.AUTHORIZATION
import com.coelhocaique.finance.api.helper.Fields.DATE_FROM
import com.coelhocaique.finance.api.helper.Fields.DATE_TO
import com.coelhocaique.finance.api.helper.Fields.ID
import com.coelhocaique.finance.api.helper.Fields.NAME
import com.coelhocaique.finance.api.helper.Fields.PROPERTY_NAME
import com.coelhocaique.finance.api.helper.Fields.REF_CODE
import com.coelhocaique.finance.api.helper.Fields.REF_DATE
import com.coelhocaique.finance.api.helper.Messages.INVALID_ID
import com.coelhocaique.finance.api.helper.Messages.INVALID_REF_CODE
import com.coelhocaique.finance.api.helper.Messages.INVALID_REQUEST
import com.coelhocaique.finance.api.helper.Messages.MISSING_HEADERS
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.unauthorized
import com.coelhocaique.finance.core.util.formatToUUID
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
                             parameterName = retrieveParameterName(req),
                             propertyName = retrievePropertyName(req)) }
    }

    fun retrievePath(req: ServerRequest): Mono<FetchCriteria> {
        return retrieveAccountId(req)
                .map { FetchCriteria(accountId = it, id = retrieveId(req)) }
    }

    fun retrieveAccountId(req: ServerRequest): Mono<String> {
        val account = req.headers().header(AUTHORIZATION)
        return try {
            just(formatToUUID(account[0]).toString())
        }catch (e: Exception){
            error { unauthorized(MISSING_HEADERS) }
        }
    }

    inline fun <reified T> extractBody(req: ServerRequest): Mono<T> {
        return req.bodyToMono(T::class.java)
                .onErrorMap { business(INVALID_REQUEST, it) }
    }

    private fun retrieveId(req: ServerRequest): String {
        return try {
            formatToUUID(req.pathVariable(ID)).toString()
        }catch (e: Exception){
            throw business(INVALID_ID)
        }
    }

    private fun retrieveReferenceCode(req: ServerRequest): String? {
        return try {
            req.queryParam(REF_CODE)
                    .map { formatToUUID(it).toString() }
                    .orElse(null)
        }catch (e: Exception){
            throw business(INVALID_REF_CODE)
        }
    }

    private fun retrieveParameterName(req: ServerRequest): String? {
        return req.queryParam(NAME).orElse(null)
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