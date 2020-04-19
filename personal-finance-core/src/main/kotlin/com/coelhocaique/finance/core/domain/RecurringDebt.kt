package com.coelhocaique.finance.core.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class RecurringDebt (
        val recurringDebtId: UUID,
        val amount: BigDecimal,
        val description: String,
        val type: String,
        val tag: String,
        val accountId: UUID,
        val creationDate: LocalDateTime
)