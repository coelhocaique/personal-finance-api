package com.coelhocaique.finance.core.domain.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class IncomeRequest (
        val grossAmount: BigDecimal,
        val description: String,
        val receiptDate: LocalDate,
        val referenceDate: LocalDate,
        val sourceName: String,
        val accountId: UUID,
        val discounts: List<DiscountDTO> = emptyList(),
        val additions: List<AdditionDTO> = emptyList()
)

data class IncomeResponse (
        val incomeId: UUID,
        val grossAmount: BigDecimal,
        val netAmount: BigDecimal,
        val additionalAmount: BigDecimal,
        val discountAmount: BigDecimal,
        val description: String,
        val receiptDate: LocalDate,
        val referenceDate: String,
        val sourceName: String,
        val discounts: List<DiscountDTO> = emptyList(),
        val additions: List<AdditionDTO> = emptyList(),
        val creationDate: LocalDateTime,
        val links: List<Map<String, String>>? = null
)

data class DiscountDTO (
        val amount: BigDecimal?,
        val description: String?
)

data class AdditionDTO (
        val amount: BigDecimal?,
        val description: String?
)