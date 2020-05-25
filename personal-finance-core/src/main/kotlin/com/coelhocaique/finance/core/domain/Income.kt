package com.coelhocaique.finance.core.domain

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Income (
        val incomeId: UUID,
        val grossAmount: BigDecimal,
        val netAmount: BigDecimal,
        val description: String,
        val receiptDate: LocalDate,
        val referenceDate: String,
        val sourceName: String,
        val additionalAmount: BigDecimal,
        val discountAmount: BigDecimal,
        val discounts: List<Discount>,
        val additions: List<Addition>,
        val accountId: UUID,
        val creationDate: LocalDateTime
)

