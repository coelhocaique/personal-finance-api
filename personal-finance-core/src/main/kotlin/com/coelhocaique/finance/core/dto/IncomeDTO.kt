package com.coelhocaique.finance.core.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class IncomeDTO (
        val id: String? = null,
        val description: String,
        val date: LocalDate,
        val referenceMonth: Int,
        val referenceYear: Int,
        val companyName: String,
        val owner: String,
        val discounts: List<DiscountDTO> = emptyList(),
        val additions: List<AdditionDTO> = emptyList(),
        val discountAmount: BigDecimal,
        val extraAmount: BigDecimal,
        val grossAmount: BigDecimal,
        val netAmount: BigDecimal,
        val creationDate: LocalDateTime? = null
)