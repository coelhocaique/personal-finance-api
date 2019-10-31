package com.coelhocaique.finance.core.domain.dto

import java.time.LocalDateTime
import java.util.*

data class ParameterDTO (
        val parameterId: UUID? = null,
        val name: String,
        val value: String,
        val startDate: String,
        val endDate: String? = null,
        val creationDate: LocalDateTime? = null,
        val links: List<Map<String, String>>? = null
)