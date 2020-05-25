package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Parameter
import com.coelhocaique.finance.core.domain.dto.ParameterRequest
import com.coelhocaique.finance.core.domain.dto.ParameterResponse
import com.coelhocaique.finance.core.util.generateReferenceDate
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.*

object ParameterMapper {

    fun toDocument(
            dto: ParameterRequest
    ): Parameter = Parameter(
            parameterId = UUID.randomUUID(),
            parameterName = dto.name,
            parameterValue = dto.value,
            referenceDate = generateReferenceDate(dto.referenceDate),
            accountId = dto.accountId,
            creationDate = LocalDateTime.now()
    )

    fun toResponse(
            document: Parameter
    ): ParameterResponse = ParameterResponse(
            parameterId = document.parameterId,
            name = document.parameterName,
            value = document.parameterValue,
            referenceDate = document.referenceDate,
            creationDate = document.creationDate
    )

    fun toMonoResponse(parameter: Parameter): Mono<ParameterResponse> = just(toResponse(parameter))
}

