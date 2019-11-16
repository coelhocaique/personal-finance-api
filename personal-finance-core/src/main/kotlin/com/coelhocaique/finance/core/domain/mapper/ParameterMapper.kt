package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Parameter
import com.coelhocaique.finance.core.domain.dto.ParameterDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.*

object ParameterMapper {

    fun toDocument(dto: ParameterDTO): Parameter =
            Parameter(parameterId = UUID.randomUUID(),
                    name = dto.name,
                    value = dto.value,
                    referenceDate = dto.referenceDate,
                    accountId = dto.accountId!!,
                    creationDate = LocalDateTime.now())

    fun toDTO(document: Parameter): ParameterDTO =
            ParameterDTO(
                    parameterId= document.parameterId,
                    name = document.name,
                    value = document.value,
                    referenceDate = document.referenceDate,
                    creationDate = document.creationDate)

    fun toMonoDTO(parameter: Parameter): Mono<ParameterDTO> = just(toDTO(parameter))

}

