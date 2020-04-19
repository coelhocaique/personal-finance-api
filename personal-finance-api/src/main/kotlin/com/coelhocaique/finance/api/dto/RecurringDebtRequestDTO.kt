package com.coelhocaique.finance.api.dto

import java.math.BigDecimal

data class RecurringDebtRequestDTO (
        val amount: BigDecimal?,
        val description: String?,
        val type: String?,
        val tag: String?,
        val referenceCode: String?,
        val accountId: String?
)