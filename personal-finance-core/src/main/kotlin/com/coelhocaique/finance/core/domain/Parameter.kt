package com.coelhocaique.finance.core.domain

import java.time.LocalDateTime
import java.util.*

data class Parameter (
        val parameterId: UUID,
        val name: String,
        val value: String,
        val referenceDate: String,
        val accountId: UUID,
        val creationDate: LocalDateTime
)