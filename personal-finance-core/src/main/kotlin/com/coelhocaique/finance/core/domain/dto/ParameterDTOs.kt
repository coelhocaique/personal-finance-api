package com.coelhocaique.finance.core.domain.dto

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class ParameterRequest (
        val name: String,
        val value: String,
        val referenceDate: LocalDate,
        val accountId: UUID
)

data class ParameterResponse (
        val parameterId: UUID,
        val name: String,
        val value: String,
        val referenceDate: String,
        val creationDate: LocalDateTime,
        val links: List<Map<String, String>>? = null
)