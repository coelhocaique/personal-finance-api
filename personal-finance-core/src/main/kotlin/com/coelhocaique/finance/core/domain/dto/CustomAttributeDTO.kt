package com.coelhocaique.finance.core.domain.dto

import java.time.LocalDateTime
import java.util.UUID

data class CustomAttributeDTO (
        val customAttributeId: UUID? = null,
        val propertyName: String,
        val value: String,
        val accountId: String? = null,
        val creationDate: LocalDateTime? = null,
        val links: List<Map<String, String>>? = null
)