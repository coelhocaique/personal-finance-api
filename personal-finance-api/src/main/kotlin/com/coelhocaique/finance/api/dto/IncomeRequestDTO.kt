package com.coelhocaique.finance.api.dto

import com.coelhocaique.finance.core.domain.dto.AdditionDTO
import com.coelhocaique.finance.core.domain.dto.DiscountDTO
import java.math.BigDecimal
import java.time.LocalDate

data class IncomeRequestDTO (
        val grossAmount: BigDecimal? = null,
        val description: String? = null,
        val receiptDate: LocalDate? = null,
        val referenceDate: String? = null,
        val sourceName: String? = null,
        val username: String = "COELHOCAIQUE",
        val discounts: List<DiscountDTO> = emptyList(),
        val additions: List<AdditionDTO> = emptyList()
)