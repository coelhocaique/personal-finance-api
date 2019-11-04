package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Parameter
import com.coelhocaique.finance.core.domain.dto.ParameterDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.*

object ParameterMapper {

    fun toDocument(dto: ParameterDTO): Parameter =
            Parameter(id = UUID.randomUUID().toString(),
                    name = dto.name,
                    value = dto.value,
                    referenceDate = dto.referenceDate,
                    creationDate = dto.creationDate,
                    userId = dto.userId)

    fun toDTO(parameter: Parameter): ParameterDTO =
            ParameterDTO(
                    parameterId= UUID.fromString(parameter.id),
                    name = parameter.name!!,
                    value = parameter.value!!,
                    referenceDate = parameter.referenceDate!!,
                    creationDate = parameter.creationDate,
                    userId = parameter.userId!!)

    fun toMonoDTO(parameter: Parameter): Mono<ParameterDTO> = just(toDTO(parameter))

}

