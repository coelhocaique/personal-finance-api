package com.coelhocaique.finance.core.dto

import java.math.BigDecimal

data class AdditionDTO (
        val amount: BigDecimal,
        val description: String,
        val id: String? = null,
        val incomeId: String? = null
)
