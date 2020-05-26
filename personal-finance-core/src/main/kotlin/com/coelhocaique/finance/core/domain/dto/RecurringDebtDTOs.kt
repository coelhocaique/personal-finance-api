package com.coelhocaique.finance.core.domain.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class RecurringDebtRequest (
        val amount: BigDecimal,
        val description: String,
        val type: String,
        val tag: String,
        val accountId: UUID
)

data class RecurringDebtResponse (
        val recurringDebtId: UUID,
        val amount: BigDecimal,
        val description: String,
        val type: String,
        val tag: String,
        val creationDate: LocalDateTime,
        val links: List<Map<String, String>>? = null
)