package com.coelhocaique.finance.core.domain

import java.math.BigDecimal

data class Discount (
        val amount: BigDecimal,
        val description: String
)