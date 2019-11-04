package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.unauthorized
import org.springframework.web.reactive.function.server.ServerRequest

object RequestParameterHandler {

    fun retrieveParameters(req: ServerRequest): FetchCriteria {
        val userId = retrieveUserId(req)
        val referenceCode = retrieveReferenceCode(req)
        val referenceDate = retrieveReferenceDate(req)
        val referenceDateFrom = retrieveDateFrom(req)
        val referenceDateTo = retrieveDateTo(req)
        return FetchCriteria(userId = userId,
                             referenceCode = referenceCode,
                             referenceDate = referenceDate,
                             dateFrom = referenceDateFrom,
                             dateTo = referenceDateTo)
    }

    fun retrievePath(req: ServerRequest): FetchCriteria {
        val userId = retrieveUserId(req)
        val id = retrieveId(req)
        return FetchCriteria(userId = userId, id = id)
    }

    private fun retrieveId(req: ServerRequest): String {
        return req.pathVariable("id")
    }

    private fun retrieveUserId(req: ServerRequest): String {
        val user = req.headers().header("user_id")
        return if (user.size > 0) user[0] else throw unauthorized("Missing request header.")
    }

    private fun retrieveReferenceCode(req: ServerRequest): String? {
        return req.queryParam("reference_code").orElse(null)
    }

    private fun retrieveReferenceDate(req: ServerRequest): String? {
        return req.queryParam("reference_date").orElse(null)
    }

    private fun retrieveDateFrom(req: ServerRequest): String? {
        return req.queryParam("date_from").orElse(null)
    }

    private fun retrieveDateTo(req: ServerRequest): String? {
        return req.queryParam("date_to").orElse(null)
    }
}