package com.coelhocaique.finance.core.document

import java.math.BigDecimal

data class Addition (
        val amount: BigDecimal,
        val description: String
)