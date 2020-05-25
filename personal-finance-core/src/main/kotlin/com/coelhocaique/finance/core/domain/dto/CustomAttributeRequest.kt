package com.coelhocaique.finance.core.domain.dto

import java.time.LocalDateTime
import java.util.UUID

data class CustomAttributeRequest (
        val propertyName: String,
        val value: String,
        val accountId: UUID
)

data class CustomAttributeResponse (
        val customAttributeId: UUID,
        val propertyName: String,
        val value: String,
        val creationDate: LocalDateTime,
        val links: List<Map<String, String>>? = null
)