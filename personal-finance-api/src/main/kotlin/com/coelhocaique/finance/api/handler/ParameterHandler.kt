package com.coelhocaique.finance.api.handler

import org.springframework.web.reactive.function.server.ServerRequest

object ParameterHandler {

    fun retrieveParameters(req: ServerRequest): FetchCriteria {
        val referenceCode = retrieveReferenceCode(req)
        val referenceDate = retrieveReferenceDate(req)
        val referenceDateFrom = retrieveDateFrom(req)
        val referenceDateTo = retrieveDateTo(req)
        return FetchCriteria(referenceCode, referenceDate, referenceDateFrom, referenceDateTo)
    }

    fun retrieveId(req: ServerRequest): String{
        return req.pathVariable("id")
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