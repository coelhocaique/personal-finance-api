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
        val discounts: List<Discount> = emptyList(),
        val additions: List<Addition> = emptyList(),
        val accountId: UUID,
        val creationDate: LocalDateTime
)

data class Discount (
        val amount: BigDecimal,
        val description: String
)

data class Addition (
        val amount: BigDecimal,
        val description: String
)