package com.coelhocaique.finance.api.dto

import com.coelhocaique.finance.core.domain.dto.AdditionDTO
import com.coelhocaique.finance.core.domain.dto.DiscountDTO
import java.math.BigDecimal
import java.time.LocalDate

data class IncomeRequestDTO (
        val grossAmount: BigDecimal,
        val description: String,
        val receiptDate: LocalDate,
        val referenceDate: String,
        val sourceName: String,
        val userId: String = "44df25d4-08a4-4e3e-9a3b-8b1e39483380",
        val discounts: List<DiscountDTO> = emptyList(),
        val additions: List<AdditionDTO> = emptyList()
)