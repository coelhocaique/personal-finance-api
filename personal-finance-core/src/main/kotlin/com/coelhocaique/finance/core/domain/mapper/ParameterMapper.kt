package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Parameter
import com.coelhocaique.finance.core.domain.dto.ParameterDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.*

object ParameterMapper {

    fun toDocument(dto: ParameterDTO): Parameter =
            Parameter(id = UUID.randomUUID().toString(),
                    name = dto.name,
                    value = dto.value,
                    referenceDate = dto.referenceDate,
                    accountId = dto.accountId,
                    creationDate = LocalDateTime.now())

    fun toDTO(parameter: Parameter): ParameterDTO =
            ParameterDTO(
                    parameterId= UUID.fromString(parameter.id),
                    name = parameter.name!!,
                    value = parameter.value!!,
                    referenceDate = parameter.referenceDate!!,
                    creationDate = parameter.creationDate)

    fun toMonoDTO(parameter: Parameter): Mono<ParameterDTO> = just(toDTO(parameter))

}

