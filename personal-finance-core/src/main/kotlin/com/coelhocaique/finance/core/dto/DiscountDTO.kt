package com.coelhocaique.finance.core.dto

import java.math.BigDecimal

data class DiscountDTO (
        val amount: BigDecimal,
        val description: String
)