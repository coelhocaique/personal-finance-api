package com.coelhocaique.finance.core.domain.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class IncomeDTO (
        val incomeId: UUID? = null,
        val grossAmount: BigDecimal,
        val netAmount: BigDecimal? = null,
        val additionalAmount: BigDecimal? = null,
        val discountAmount: BigDecimal? = null,
        val description: String,
        val receiptDate: LocalDate,
        val referenceDate: String,
        val sourceName: String,
        val accountId: String? = null,
        val discounts: List<DiscountDTO>? = emptyList(),
        val additions: List<AdditionDTO>? = emptyList(),
        val creationDate: LocalDateTime? = null,
        val links: List<Map<String, String>>? = null
)