package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.helper.Fields.DATE_FROM
import com.coelhocaique.finance.api.helper.Fields.DATE_TO
import com.coelhocaique.finance.api.helper.Fields.ID
import com.coelhocaique.finance.api.helper.Fields.REF_CODE
import com.coelhocaique.finance.api.helper.Fields.REF_DATE
import com.coelhocaique.finance.api.helper.Fields.USER_ID
import com.coelhocaique.finance.api.helper.Messages.MISSING_HEADERS
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.unauthorized
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

object RequestParameterHandler {

    fun retrieveParameters(req: ServerRequest): Mono<FetchCriteria> {
        return retrieveUserId(req)
                    .map { FetchCriteria(userId = it,
                             referenceCode = retrieveReferenceCode(req),
                             referenceDate = retrieveReferenceDate(req),
                             dateFrom = retrieveDateFrom(req),
                             dateTo = retrieveDateTo(req)) }

    }

    fun retrievePath(req: ServerRequest): Mono<FetchCriteria> {
        return retrieveUserId(req)
                .map { FetchCriteria(userId = it, id = retrieveId(req)) }
    }

    private fun retrieveId(req: ServerRequest): String {
        return req.pathVariable(ID)
    }

    private fun retrieveUserId(req: ServerRequest): Mono<String> {
        val user = req.headers().header(USER_ID)
        return if (user.size > 0)
            just(user[0])
        else
            error { unauthorized(MISSING_HEADERS) }
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
}