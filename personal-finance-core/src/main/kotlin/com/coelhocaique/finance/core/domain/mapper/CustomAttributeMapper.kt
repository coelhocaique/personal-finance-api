package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.CustomAttribute
import com.coelhocaique.finance.core.domain.dto.CustomAttributeDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.*

object CustomAttributeMapper {

    fun toDocument(dto: CustomAttributeDTO): CustomAttribute =
            CustomAttribute(id = UUID.randomUUID().toString(),
                    propertyName = dto.propertyName.toLowerCase(),
                    value = dto.value,
                    accountId = dto.accountId,
                    creationDate = LocalDateTime.now())

    fun toDTO(customAttribute: CustomAttribute): CustomAttributeDTO =
            CustomAttributeDTO(
                    customAttributeId = UUID.fromString(customAttribute.id),
                    value = customAttribute.value!!,
                    propertyName = customAttribute.propertyName!!,
                    creationDate = customAttribute.creationDate)

    fun toMonoDTO(customAttribute: CustomAttribute): Mono<CustomAttributeDTO> = just(toDTO(customAttribute))

}

