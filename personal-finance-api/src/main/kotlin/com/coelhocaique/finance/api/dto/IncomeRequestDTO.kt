package com.coelhocaique.finance.api.dto

import com.coelhocaique.finance.core.dto.AdditionDTO
import com.coelhocaique.finance.core.dto.DiscountDTO
import com.coelhocaique.finance.core.enums.Username
import java.math.BigDecimal
import java.time.LocalDate

data class IncomeRequestDTO (
        val grossAmount: BigDecimal? = null,
        val description: String? = null,
        val receiptDate: LocalDate? = null,
        val referenceDate: String? = null,
        val sourceName: String? = null,
        val username: Username = Username.COELHOCAIQUE,
        val discounts: List<DiscountDTO> = emptyList(),
        val additions: List<AdditionDTO> = emptyList()
)