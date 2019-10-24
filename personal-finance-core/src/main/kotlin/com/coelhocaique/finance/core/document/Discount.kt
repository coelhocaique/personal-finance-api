package com.coelhocaique.finance.core.document

import java.math.BigDecimal

data class Discount (
        val amount: BigDecimal,
        val description: String
)