package com.coelhocaique.finance.core.domain.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class RecurringDebtDTO (
        val recurringDebtId: UUID? = null,
        val amount: BigDecimal,
        val description: String,
        val type: String,
        val tag: String,
        val accountId: UUID? = null,
        val creationDate: LocalDateTime? = null,
        val links: List<Map<String, String>>? = null
)