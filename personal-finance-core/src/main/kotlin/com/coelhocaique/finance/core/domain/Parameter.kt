package com.coelhocaique.finance.core.domain

import java.time.LocalDateTime
import java.util.*

data class Parameter (
        val parameterId: UUID,
        val parameterName: String,
        val parameterValue: String,
        val referenceDate: String,
        val accountId: UUID,
        val creationDate: LocalDateTime
)