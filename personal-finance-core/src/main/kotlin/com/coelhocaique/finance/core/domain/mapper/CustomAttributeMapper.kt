package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.CustomAttribute
import com.coelhocaique.finance.core.domain.dto.CustomAttributeRequest
import com.coelhocaique.finance.core.domain.dto.CustomAttributeResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.*

object CustomAttributeMapper {

    fun toDocument(
            request: CustomAttributeRequest
    ): CustomAttribute = CustomAttribute(
            customAttributeId = UUID.randomUUID(),
            propertyName = request.propertyName.toLowerCase(),
            value = request.value,
            accountId = request.accountId,
            creationDate = LocalDateTime.now()
    )

    fun toDTO(
            document: CustomAttribute
    ): CustomAttributeResponse = CustomAttributeResponse(
            customAttributeId = document.customAttributeId,
            value = document.value,
            propertyName = document.propertyName,
            creationDate = document.creationDate
    )

    fun toMonoDTO(customAttribute: CustomAttribute): Mono<CustomAttributeResponse> = just(toDTO(customAttribute))

}

